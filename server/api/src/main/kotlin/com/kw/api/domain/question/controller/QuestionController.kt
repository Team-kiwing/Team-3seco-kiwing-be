package com.kw.api.domain.question.controller

import com.kw.api.common.dto.response.ApiResponse
import com.kw.api.domain.question.dto.request.QuestionAnswerRequest
import com.kw.api.domain.question.dto.request.QuestionCreateRequest
import com.kw.api.domain.question.dto.request.QuestionSearchRequest
import com.kw.api.domain.question.dto.request.QuestionUpdateRequest
import com.kw.api.domain.question.dto.response.QuestionListResponse
import com.kw.api.domain.question.dto.response.QuestionReportResponse
import com.kw.api.domain.question.dto.response.QuestionResponse
import com.kw.api.domain.question.service.QuestionService
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1")
@RestController
class QuestionController(val questionService: QuestionService) {

    @PostMapping("/questions")
    fun createQuestion(@ModelAttribute questionCreateRequest: QuestionCreateRequest): ApiResponse<QuestionResponse> {
        val response = questionService.createQuestion(questionCreateRequest)
        return ApiResponse.created(response);
    }

    @PostMapping("/questions/{id}/answer")
    fun createAnswer(
        @RequestBody answerRequest: QuestionAnswerRequest,
        @PathVariable id: Long
    ): ApiResponse<QuestionResponse> {
        val response = questionService.createAnswer(id, answerRequest)
        return ApiResponse.ok(response)
    }

    @PatchMapping("/questions/{id}/content")
    fun updateQuestionContent(
        @RequestBody request: QuestionUpdateRequest,
        @PathVariable id: Long
    ): ApiResponse<QuestionResponse> {
        val response = questionService.updateQuestionContent(id, request)
        return ApiResponse.ok(response)
    }

    @PatchMapping("/questions/{id}/status")
    fun updateQuestionStatus(
        @RequestParam shareStatus: String,
        @PathVariable id: Long
    ): ApiResponse<QuestionResponse> {
        val response = questionService.updateQuestionStatus(id, shareStatus)
        return ApiResponse.ok(response)
    }

    @PostMapping("/questions/{id}/copy")
    fun createQuestionCopy(@PathVariable id: Long): ApiResponse<QuestionResponse> {
        val response = questionService.createQuestionCopy(id)
        return ApiResponse.ok(response)
    }

    @PostMapping("/question/{id}/report")
    fun reportQuestion(
        @RequestParam reason: String,
        @PathVariable id: Long
    ): ApiResponse<QuestionReportResponse> {
        val response = questionService.reportQuestion(reason, id)
        return ApiResponse.created(response)
    }

    @GetMapping("/question/search")
    fun searchQuestion(@ModelAttribute questionSearchRequest: QuestionSearchRequest) : ApiResponse<QuestionListResponse> {
        val responses = questionService.searchQuestion(questionSearchRequest)
        return ApiResponse.ok(responses)
    }

    @PatchMapping("/question/{id}/tags")
    fun updateQuestionTags(@RequestParam tagIds : List<Long>?,
                           @PathVariable id : Long) {
        questionService.updateQuestionQuestionTags(tagIds, id)
    }
}
