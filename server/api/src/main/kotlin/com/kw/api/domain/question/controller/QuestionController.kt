package com.kw.api.domain.question.controller

import com.kw.api.domain.question.dto.request.QuestionCreateRequest
import com.kw.api.domain.question.dto.request.QuestionReportRequest
import com.kw.api.domain.question.dto.request.QuestionSearchRequest
import com.kw.api.domain.question.dto.request.QuestionUpdateRequest
import com.kw.api.domain.question.dto.response.QuestionListResponse
import com.kw.api.domain.question.dto.response.QuestionReportResponse
import com.kw.api.domain.question.dto.response.QuestionResponse
import com.kw.api.domain.question.service.QuestionService
import com.kw.data.domain.member.Member
import com.kw.infrasecurity.resolver.AuthToMember
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "질문")
@RequestMapping("/api/v1")
@RestController
class QuestionController(val questionService: QuestionService) {

    @Operation(summary = "질문 생성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/questions")
    fun createQuestion(
        @RequestBody @Valid request: QuestionCreateRequest,
        @AuthToMember member: Member
    ): QuestionResponse {
        return questionService.createQuestion(request, member)
    }

    @Operation(summary = "질문 수정")
    @PatchMapping("/questions/{id}")
    fun updateQuestion(
        @PathVariable id: Long,
        @RequestBody @Valid request: QuestionUpdateRequest,
        @AuthToMember member: Member
    ): QuestionResponse {
        return questionService.updateQuestion(id, request, member)
    }

    @Operation(summary = "질문 삭제")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/questions/{id}")
    fun deleteQuestion(
        @PathVariable id: Long,
        @AuthToMember member: Member
    ) {
        questionService.deleteQuestion(id, member)
    }

    @Operation(summary = "질문 신고")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/questions/{id}/report")
    fun reportQuestion(
        @RequestBody reason: QuestionReportRequest,
        @PathVariable id: Long
    ): QuestionReportResponse {
        return questionService.reportQuestion(reason, id)
    }

    @Operation(summary = "질문 검색")
    @GetMapping("/questions/search")
    fun searchQuestion(@ParameterObject @ModelAttribute questionSearchRequest: QuestionSearchRequest): QuestionListResponse {
        return questionService.searchQuestion(questionSearchRequest)
    }
}
