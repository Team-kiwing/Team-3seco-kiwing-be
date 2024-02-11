package com.kw.api.domain.question.controller

import com.kw.api.domain.question.dto.request.QuestionAnswerRequest
import com.kw.api.domain.question.dto.request.QuestionCreateRequest
import com.kw.api.domain.question.service.QuestionService
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1")
@RestController
class QuestionController(val questionService : QuestionService) {

    @PostMapping("/questions")
    fun postQuestion(@ModelAttribute questionCreateRequest: QuestionCreateRequest) {
        val response = questionService.postQuestion(questionCreateRequest)
        return
    }

    @PostMapping("/questions/{id}/answer")
    fun postAnswer(@RequestBody answerRequest: QuestionAnswerRequest,
                   @PathVariable id : Long){
        val response = questionService.postAnswer(id, answerRequest)
        return
    }
}
