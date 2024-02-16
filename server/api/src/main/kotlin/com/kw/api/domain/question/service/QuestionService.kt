package com.kw.api.domain.question.service

import com.kw.api.domain.question.dto.request.QuestionAnswerRequest
import com.kw.api.domain.question.dto.request.QuestionCreateRequest
import com.kw.api.domain.question.dto.request.QuestionSearchRequest
import com.kw.api.domain.question.dto.request.QuestionUpdateRequest
import com.kw.api.domain.question.dto.response.QuestionReportResponse
import com.kw.api.domain.question.dto.response.QuestionResponse
import com.kw.data.domain.question.Question
import com.kw.data.domain.question.QuestionReport
import com.kw.data.domain.question.QuestionTag
import com.kw.data.domain.question.repository.QuestionReportRepository
import com.kw.data.domain.question.repository.QuestionRepository
import com.kw.data.domain.tag.repository.TagRepository
import com.kw.infraquerydsl.domain.question.QuestionCustomRepository
import com.kw.infraquerydsl.domain.question.dto.QuestionSearchDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.RuntimeException

@Service
@Transactional
class QuestionService(val questionRepository : QuestionRepository,
        val questionReportRepository : QuestionReportRepository,
    val questionCustomRepository: QuestionCustomRepository,
    val tagRepository: TagRepository) {
    fun createQuestion(questionCreateRequest: QuestionCreateRequest) : QuestionResponse {
        val question = questionRepository.save(questionCreateRequest.toEntity())
        val questionTags = getQuestionTags(questionCreateRequest.tagIds, question)

        question.updateQuestionQuestionTags(questionTags)
        val tagIds = getQuestionTagIds(question)
        return QuestionResponse.from(question, tagIds)
    }

    fun createAnswer(id: Long, answerRequest: QuestionAnswerRequest) : QuestionResponse {
        val question = getQuestion(id)
        question.updateQuestionAnswer(answerRequest.answer)
        val tagIds = getQuestionTagIds(question)
        return QuestionResponse.from(question, tagIds)
    }

    fun updateQuestionContent(id: Long, request: QuestionUpdateRequest) : QuestionResponse {
        val question = getQuestion(id)
        question.updateQuestionContent(request.content)
        val tagIds = getQuestionTagIds(question)
        return QuestionResponse.from(question, tagIds)
    }

    fun updateQuestionStatus(id: Long, status: String): QuestionResponse {
        val question = getQuestion(id)
        question.updateQuestionStatus(Question.ShareStatus.valueOf(status));
        val tagIds = getQuestionTagIds(question)
        return QuestionResponse.from(question, tagIds);
    }

    fun createQuestionCopy(id: Long) : QuestionResponse {
        val question = getQuestion(id)
        question.increaseShareCount()

        val copyQuestion = Question(content = question.content,
                originId = question.id,
                shareStatus = Question.ShareStatus.NON_AVAILABLE)
        val tagIds = getQuestionTagIds(question)
        return QuestionResponse.from(questionRepository.save(copyQuestion), tagIds)
    }

    fun reportQuestion(reason: String, id: Long) : QuestionReportResponse {
        val question = getQuestion(id)

        val report = QuestionReport(reason = reason,
                question = question)
        return QuestionReportResponse.from(questionReportRepository.save(report))
    }

    @Transactional(readOnly = true)
    fun searchQuestion(questionSearchRequest: QuestionSearchRequest): List<QuestionResponse> {
        val questionSearchDto = QuestionSearchDto(keyword = questionSearchRequest.keyword,
            page = questionSearchRequest.page)
        val questions = questionCustomRepository.searchQuestion(questionSearchDto)
        return questions.map {
            question ->
            val tagIds = getQuestionTagIds(question)
            QuestionResponse.from(question, tagIds)
        }
    }

    fun updateQuestionQuestionTags(tagIds: List<Long>?, questionId: Long) {
        val question = getQuestion(questionId)
        val questionTags = getQuestionTags(tagIds, question)

        question.updateQuestionQuestionTags(questionTags)
    }

    private fun getQuestionTagIds(question: Question) : List<Long?>? {
        return question.questionTags?.map { questionTag ->
            questionTag.tag.id
        }
    }

    private fun getQuestionTags(
        tagIds: List<Long?>?,
        question: Question
    ): List<QuestionTag>? {
        val tags = tagIds?.map { tagId ->
            tagRepository.findById(tagId!!)
                .orElseThrow { throw RuntimeException("tag가 존재하지 않습니다.") }
        }

        val questionTags = tags?.map { tag ->
            QuestionTag(question, tag)
        }
        return questionTags
    }

    private fun getQuestion(id: Long) : Question {
        return questionRepository.findById(id)
                .orElseThrow{throw RuntimeException("question이 존재하지 않습니다.")}
    }
}
