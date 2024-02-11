package com.kw.api.domain.question.service

import com.kw.api.domain.question.dto.request.QuestionAnswerRequest
import com.kw.api.domain.question.dto.request.QuestionCreateRequest
import com.kw.api.domain.question.dto.response.QuestionResponse
import com.kw.data.domain.question.Question
import com.kw.data.domain.question.QuestionReport
import com.kw.data.domain.question.repository.QuestionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.RuntimeException

@Service
@Transactional
class QuestionService(val questionRepository : QuestionRepository) {
    fun postQuestion(questionCreateRequest: QuestionCreateRequest) : QuestionResponse {
        val question = questionRepository.save(questionCreateRequest.toEntity())
        return QuestionResponse.of(question)
    }

    fun postAnswer(id: Long, answerRequest: QuestionAnswerRequest) : QuestionResponse {
        val question = getQuestion(id)
        question.updateQuestionAnswer(answerRequest.answer);
        return QuestionResponse.of(question)
    }

    private fun getQuestion(id: Long) : Question {
        return questionRepository.findById(id)
                .orElseThrow{throw RuntimeException("question이 존재하지 않습니다.")}
    }
}
