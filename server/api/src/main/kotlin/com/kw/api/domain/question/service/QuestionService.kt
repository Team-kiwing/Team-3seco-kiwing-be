package com.kw.api.domain.question.service

import com.kw.api.common.dto.PageResponse
import com.kw.api.common.exception.ApiErrorCode
import com.kw.api.common.exception.ApiException
import com.kw.api.common.properties.ApiProperties
import com.kw.api.domain.question.dto.request.QuestionCreateRequest
import com.kw.api.domain.question.dto.request.QuestionReportRequest
import com.kw.api.domain.question.dto.request.QuestionSearchRequest
import com.kw.api.domain.question.dto.request.QuestionUpdateRequest
import com.kw.api.domain.question.dto.response.QuestionReportResponse
import com.kw.api.domain.question.dto.response.QuestionResponse
import com.kw.api.domain.question.dto.response.QuestionSearchResponse
import com.kw.data.domain.bundle.repository.BundleRepository
import com.kw.data.domain.member.Member
import com.kw.data.domain.question.Question
import com.kw.data.domain.question.QuestionReport
import com.kw.data.domain.question.QuestionTag
import com.kw.data.domain.question.dto.QuestionSearchDto
import com.kw.data.domain.question.repository.QuestionReportRepository
import com.kw.data.domain.question.repository.QuestionRepository
import com.kw.data.domain.tag.Tag
import com.kw.data.domain.tag.repository.TagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class QuestionService(
    private val apiProperties: ApiProperties,
    private val questionRepository: QuestionRepository,
    private val questionReportRepository: QuestionReportRepository,
    private val tagRepository: TagRepository,
    private val bundleRepository: BundleRepository,
) {
    fun createQuestion(request: QuestionCreateRequest, member: Member): QuestionResponse {
        val bundle = bundleRepository.findById(request.bundleId)
            .orElseThrow { ApiException(ApiErrorCode.NOT_FOUND_BUNDLE) }
        if (!bundle.isWriter(member.id)) {
            throw ApiException(ApiErrorCode.FORBIDDEN)
        }

        val questionCount = questionRepository.countAllByBundleId(request.bundleId)
        if (questionCount >= 100) {
            throw ApiException(ApiErrorCode.OVER_QUESTION_LIMIT)
        }

        val question = request.toEntity(bundle, member)
        val tags = request.tagIds?.let { getExistTags(it) } ?: emptyList()
        question.updateQuestionTags(tags.map { QuestionTag(question, it) })

        val savedQuestion = questionRepository.save(question)
        bundle.updateQuestionOrder((bundle.questionOrder + " " + savedQuestion.id).trim())

        return QuestionResponse.from(savedQuestion, member.id, apiProperties.hotThreshold)
    }

    fun updateQuestion(id: Long, request: QuestionUpdateRequest, member: Member): QuestionResponse {
        val question = getExistQuestion(id)
        if (!question.isWriter(member.id)) {
            throw ApiException(ApiErrorCode.FORBIDDEN)
        }

        request.content?.let {
            question.originId?.let { originId ->
                val origin = questionRepository.findById(originId)
                if (!isEqualToOriginContent(origin, it)) {
                    question.updateSearchableStatus(true)
                }
            }
            question.updateContent(it)
        }
        request.answer?.let { question.updateAnswer(it) }
        request.answerShareType?.let { question.updateAnswerShareStatus(it) }
        request.tagIds?.let { it ->
            val tags = getExistTags(it)
            question.updateQuestionTags(tags.map { QuestionTag(question, it) })
        }

        return QuestionResponse.from(question, member.id, apiProperties.hotThreshold)
    }

    fun deleteQuestion(id: Long, member: Member) {
        val question = getExistQuestion(id)
        if (!question.isWriter(member.id)) {
            throw ApiException(ApiErrorCode.FORBIDDEN)
        }

        if (question.originId == null) {
            val copiedQuestion = questionRepository.findNotSearchableFirstOneByOriginId(id)
            copiedQuestion?.let {
                copiedQuestion.updateSearchableStatus(true)
            }
        }

        questionRepository.delete(question)
    }

    fun reportQuestion(request: QuestionReportRequest, id: Long): QuestionReportResponse {
        val question = getExistQuestion(id)

        val report = QuestionReport(
            reason = request.reason,
            question = question
        )
        return QuestionReportResponse.from(questionReportRepository.save(report))
    }

    @Transactional(readOnly = true)
    fun searchQuestion(request: QuestionSearchRequest): QuestionSearchResponse {
        val questionSearchDto = QuestionSearchDto(
            sortingType = request.sortingType,
            tagIds = request.tagIds,
            keyword = request.keyword,
            page = request.page,
            size = request.size,
        )
        val questions = questionRepository.search(questionSearchDto)
        val questionResponses = questions.map { QuestionResponse.from(it, null, apiProperties.hotThreshold) }
        val lastPageNum = questionRepository.getPageNum(questionSearchDto)

        return QuestionSearchResponse(questionResponses, PageResponse(questionSearchDto.page, lastPageNum))
    }

    private fun getExistTags(tagIds: List<Long>): List<Tag> {
        val tags = tagRepository.findAllById(tagIds)
        if (tags.size != tagIds.size) {
            throw ApiException(ApiErrorCode.INCLUDE_NOT_FOUND_TAG)
        }
        return tags
    }

    private fun getExistQuestion(id: Long): Question {
        return questionRepository.findById(id)
            .orElseThrow { ApiException(ApiErrorCode.NOT_FOUND_QUESTION) }
    }

    private fun isEqualToOriginContent(origin: Optional<Question>, content: String): Boolean {
        return origin.isPresent && origin.get().content == content
    }
}
