package com.kw.api.domain.question.service

import com.kw.api.domain.question.dto.request.QuestionCreateRequest
import com.kw.api.domain.question.dto.response.QuestionResponse
import com.kw.data.domain.question.repository.QuestionRepository
import org.springframework.stereotype.Service

@Service
class QuestionService(val questionRepository : QuestionRepository) {
    fun postQuestion(questionCreateRequest: QuestionCreateRequest) : QuestionResponse {
        val question = questionRepository.save(questionCreateRequest.toEntity())
        return QuestionResponse.of(question)
    }
}
