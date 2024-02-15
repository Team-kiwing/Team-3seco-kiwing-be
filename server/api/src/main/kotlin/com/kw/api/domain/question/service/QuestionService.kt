package com.kw.api.domain.question.service

import com.kw.api.domain.question.dto.request.QuestionAnswerRequest
import com.kw.api.domain.question.dto.request.QuestionCreateRequest
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
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
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
        return QuestionResponse.from(question)
    }

    fun createAnswer(id: Long, answerRequest: QuestionAnswerRequest) : QuestionResponse {
        val question = getQuestion(id)
        question.updateQuestionAnswer(answerRequest.answer)
        return QuestionResponse.from(question)
    }

    fun updateQuestionContent(id: Long, request: QuestionUpdateRequest) : QuestionResponse {
        val question = getQuestion(id)
        question.updateQuestionContent(request.content)
        return QuestionResponse.from(question)
    }

    fun updateQuestionStatus(id: Long, status: String): QuestionResponse {
        val question = getQuestion(id)
        question.updateQuestionStatus(Question.ShareStatus.valueOf(status));
        return QuestionResponse.from(question);
    }

    fun createQuestionCopy(id: Long) : QuestionResponse {
        val question = getQuestion(id)
        question.increaseShareCount()

        val copyQuestion = Question(content = question.content,
                originId = question.id,
                shareStatus = Question.ShareStatus.NON_AVAILABLE)
        return QuestionResponse.from(questionRepository.save(copyQuestion))
    }

    fun reportQuestion(reason: String, id: Long) : QuestionReportResponse {
        val question = getQuestion(id)

        val report = QuestionReport(reason = reason,
                question = question)
        return QuestionReportResponse.from(questionReportRepository.save(report))
    }

    fun searchQuestion(keyword: String): List<QuestionResponse> {
        val questions = questionCustomRepository.searchQuestion(keyword)
        return questions.map {
            question ->
            QuestionResponse.from(question)
        }
    }

    fun updateQuestionQuestionTags(tagIds: List<Long>?, questionId: Long) {
        val question = getQuestion(questionId)
        val questionTags = getQuestionTags(tagIds, question)

        question.updateQuestionQuestionTags(questionTags)
    }

    private fun getQuestionTags(
        tagIds: List<Long>?,
        question: Question
    ): List<QuestionTag>? {
        val tags = tagIds?.map { tagId ->
            tagRepository.findById(tagId)
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
