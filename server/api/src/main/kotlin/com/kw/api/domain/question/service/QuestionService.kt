package com.kw.api.domain.question.service

import com.kw.api.common.dto.PageResponse
import com.kw.api.common.exception.ApiErrorCode
import com.kw.api.common.exception.ApiException
import com.kw.api.domain.question.dto.request.QuestionCreateRequest
import com.kw.api.domain.question.dto.request.QuestionSearchRequest
import com.kw.api.domain.question.dto.request.QuestionUpdateRequest
import com.kw.api.domain.question.dto.response.QuestionListResponse
import com.kw.api.domain.question.dto.response.QuestionReportResponse
import com.kw.api.domain.question.dto.response.QuestionResponse
import com.kw.data.domain.bundle.repository.BundleRepository
import com.kw.data.domain.question.Question
import com.kw.data.domain.question.QuestionReport
import com.kw.data.domain.question.QuestionTag
import com.kw.data.domain.question.repository.QuestionReportRepository
import com.kw.data.domain.question.repository.QuestionRepository
import com.kw.data.domain.tag.Tag
import com.kw.data.domain.tag.repository.TagRepository
import com.kw.infraquerydsl.domain.question.dto.QuestionSearchDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class QuestionService(
    private val questionRepository: QuestionRepository,
    private val questionReportRepository: QuestionReportRepository,
    private val tagRepository: TagRepository,
    private val bundleRepository: BundleRepository,
) {
    fun createQuestion(request: QuestionCreateRequest): QuestionResponse {
        val bundle = bundleRepository.findById(request.bundleId)
            .orElseThrow { ApiException(ApiErrorCode.NOT_FOUND_BUNDLE) }

        val question = request.toEntity(bundle)
        val tags = request.tagIds?.let { getExistTags(it) } ?: emptyList()
        question.updateQuestionTags(tags.map { QuestionTag(question, it) })

        return QuestionResponse.from(questionRepository.save(question))
    }

    fun updateQuestion(id: Long, request: QuestionUpdateRequest): QuestionResponse {
        val question = getExistQuestion(id)

        request.content?.let { question.updateContent(it) }
        request.answer?.let { question.updateAnswer(it) }
        request.answerShareType?.let { question.updateAnswerShareStatus(it) }
        request.tagIds?.let { it ->
            val tags = getExistTags(it)
            question.updateQuestionTags(tags.map { QuestionTag(question, it) })
        }

        return QuestionResponse.from(question)
    }

    fun deleteQuestion(id: Long) {
        val question = getExistQuestion(id)
        questionRepository.delete(question)
    }

    fun reportQuestion(reason: String, id: Long): QuestionReportResponse {
        val question = getExistQuestion(id)

        val report = QuestionReport(
            reason = reason,
            question = question
        )
        return QuestionReportResponse.from(questionReportRepository.save(report))
    }

    @Transactional(readOnly = true)
    fun searchQuestion(questionSearchRequest: QuestionSearchRequest): QuestionListResponse {
        val questionSearchDto = QuestionSearchDto(
            keyword = questionSearchRequest.keyword,
            page = questionSearchRequest.page,
            size = questionSearchRequest.size
        )
        val questions = questionRepository.searchQuestion(questionSearchDto)
        val questionResponses = questions.map { QuestionResponse.from(it) }
        val lastPageNum = questionRepository.getPageNum(questionSearchDto)

        return QuestionListResponse(questionResponses, PageResponse(questionSearchDto.page, lastPageNum))
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
}
