package com.kw.api.domain.bundle.controller

import com.kw.api.common.dto.request.PageCondition
import com.kw.api.common.dto.response.ApiResponse
import com.kw.api.common.dto.response.PageResponse
import com.kw.api.domain.bundle.dto.request.*
import com.kw.api.domain.bundle.dto.response.BundleDetailResponse
import com.kw.api.domain.bundle.dto.response.BundleResponse
import com.kw.api.domain.bundle.service.BundleService
import com.kw.data.domain.bundle.dto.request.BundleGetCondition
import com.kw.data.domain.bundle.dto.request.BundleSearchCondition
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

//TODO: 전체적으로 member, shareType 연동
@Tag(name = "꾸러미")
@RestController
@RequestMapping("/api/v1")
class BundleController(
    private val bundleService: BundleService
) {

    @Operation(summary = "꾸러미 생성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/bundles")
    fun createBundle(@RequestBody @Valid request: BundleCreateRequest): ApiResponse<BundleDetailResponse> {
        return ApiResponse.created(bundleService.createBundle(request))
    }

    //TODO: ES
    @Operation(summary = "꾸러미 검색")
    @GetMapping("/bundles/search")
    fun searchBundles(
        @ModelAttribute @Valid searchCondition: BundleSearchCondition,
        @ModelAttribute @Valid pageCondition: PageCondition
    ): ApiResponse<PageResponse<BundleResponse>> {
        return ApiResponse.ok(bundleService.searchBundles(searchCondition, pageCondition))
    }

    @Operation(summary = "내 꾸러미 목록 조회")
    @GetMapping("/bundles/my")
    fun getMyBundles(
        @ModelAttribute @Valid getCondition: BundleGetCondition
    ): ApiResponse<List<BundleResponse>> {
        return ApiResponse.ok(bundleService.getMyBundles(getCondition))
    }

    @Operation(summary = "꾸러미 상세 조회")
    @GetMapping("/bundles/{id}")
    fun getBundle(
        @PathVariable id: Long,
        @RequestParam("showOnlyMyQuestions", required = false, defaultValue = "false") showOnlyMyQuestions: Boolean,
    ): ApiResponse<BundleDetailResponse> {
        return ApiResponse.ok(bundleService.getBundle(id, showOnlyMyQuestions))
    }

    @Operation(summary = "꾸러미 수정")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/bundles/{id}")
    fun updateBundle(
        @PathVariable id: Long,
        @RequestBody @Valid request: BundleUpdateRequest
    ): ApiResponse<BundleResponse> {
        return ApiResponse.ok(bundleService.updateBundle(id, request))
    }

    @Operation(summary = "꾸러미 삭제")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/bundles/{id}")
    fun deleteBundle(@PathVariable id: Long) {
        return bundleService.deleteBundle(id)
    }

    @Operation(summary = "꾸러미 스크랩")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/bundles/{id}/scrape")
    fun scrapeBundle(@PathVariable id: Long) {
        return bundleService.scrapeBundle(id)
    }

    @Operation(summary = "꾸러미 내 질문 순서 변경")
    @PatchMapping("/bundles/{id}/question-order")
    fun updateQuestionOrder(
        @PathVariable id: Long,
        @RequestBody @Valid request: BundleQuestionOrderUpdateRequest
    ) {
        return bundleService.updateQuestionOrder(id, request)
    }

    @Operation(summary = "선택한 질문 꾸러미에 추가")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/bundles/{id}/questions")
    fun addQuestion(
        @PathVariable id: Long,
        @RequestBody @Valid request: BundleQuestionAddRequest
    ) {
        bundleService.addQuestion(id, request)
    }

    @Operation(summary = "선택한 질문 꾸러미에서 삭제")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/bundles/{id}/questions")
    fun removeQuestion(
        @PathVariable id: Long,
        @RequestBody @Valid request: BundleQuestionRemoveRequest
    ) {
        return bundleService.removeQuestion(id, request)
    }

}
