package com.kw.api.domain.question.controller

import com.kw.api.common.dto.response.ApiResponse
import com.kw.api.domain.question.dto.request.QuestionCreateRequest
import com.kw.api.domain.question.dto.request.QuestionSearchRequest
import com.kw.api.domain.question.dto.request.QuestionUpdateRequest
import com.kw.api.domain.question.dto.response.QuestionListResponse
import com.kw.api.domain.question.dto.response.QuestionReportResponse
import com.kw.api.domain.question.dto.response.QuestionResponse
import com.kw.api.domain.question.service.QuestionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "질문")
@RequestMapping("/api/v1")
@RestController
class QuestionController(val questionService: QuestionService) {

    @Operation(summary = "질문 생성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/questions")
    fun createQuestion(@RequestBody @Valid request: QuestionCreateRequest): ApiResponse<QuestionResponse> {
        val response = questionService.createQuestion(request)
        return ApiResponse.created(response);
    }

    @Operation(summary = "질문 수정")
    @PatchMapping("/questions/{id}")
    fun updateQuestion(
        @PathVariable id: Long,
        @RequestBody @Valid request: QuestionUpdateRequest
    ): ApiResponse<QuestionResponse> {
        val response = questionService.updateQuestion(id, request)
        return ApiResponse.ok(response)
    }

    @Operation(summary = "질문 삭제")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/questions/{id}")
    fun deleteQuestion(@PathVariable id: Long) {
        questionService.deleteQuestion(id)
    }

    @Operation(summary = "질문 신고")
    @PostMapping("/questions/{id}/report")
    fun reportQuestion(
        @RequestParam reason: String,
        @PathVariable id: Long
    ): ApiResponse<QuestionReportResponse> {
        val response = questionService.reportQuestion(reason, id)
        return ApiResponse.created(response)
    }

    @Operation(summary = "질문 검색")
    @GetMapping("/questions/search")
    fun searchQuestion(@ModelAttribute questionSearchRequest: QuestionSearchRequest): ApiResponse<QuestionListResponse> {
        val responses = questionService.searchQuestion(questionSearchRequest)
        return ApiResponse.ok(responses)
    }
}
