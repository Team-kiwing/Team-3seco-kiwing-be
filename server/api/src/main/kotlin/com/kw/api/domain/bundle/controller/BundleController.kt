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
import com.kw.data.domain.member.Member
import com.kw.infrasecurity.resolver.AuthToMember
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "꾸러미")
@RestController
@RequestMapping("/api/v1")
class BundleController(
    private val bundleService: BundleService
) {

    @Operation(summary = "꾸러미 생성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/bundles")
    fun createBundle(
        @RequestBody @Valid request: BundleCreateRequest,
        @AuthToMember member: Member
    ): ApiResponse<BundleDetailResponse> {
        return ApiResponse.created(bundleService.createBundle(request, member))
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
        @ModelAttribute @Valid getCondition: BundleGetCondition,
        @AuthToMember member: Member
    ): ApiResponse<List<BundleResponse>> {
        return ApiResponse.ok(bundleService.getMyBundles(getCondition, member))
    }

    @Operation(summary = "꾸러미 상세 조회")
    @GetMapping("/bundles/{id}")
    fun getBundle(
        @PathVariable id: Long,
        @RequestParam("showOnlyMyQuestions", required = false, defaultValue = "false") showOnlyMyQuestions: Boolean,
        @AuthToMember member: Member
    ): ApiResponse<BundleDetailResponse> {
        return ApiResponse.ok(bundleService.getBundle(id, showOnlyMyQuestions, member))
    }

    @Operation(summary = "꾸러미 수정")
    @PatchMapping("/bundles/{id}")
    fun updateBundle(
        @PathVariable id: Long,
        @RequestBody @Valid request: BundleUpdateRequest,
        @AuthToMember member: Member
    ): ApiResponse<BundleResponse> {
        return ApiResponse.ok(bundleService.updateBundle(id, request, member))
    }

    @Operation(summary = "꾸러미 삭제")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/bundles/{id}")
    fun deleteBundle(
        @PathVariable id: Long,
        @AuthToMember member: Member
    ): ApiResponse<Unit> {
        bundleService.deleteBundle(id, member)
        return ApiResponse.noContent()
    }

    @Operation(summary = "꾸러미 스크랩")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/bundles/{id}/scrape")
    fun scrapeBundle(
        @PathVariable id: Long,
        @AuthToMember member: Member
    ): ApiResponse<Unit> {
        bundleService.scrapeBundle(id, member)
        return ApiResponse.created(Unit)
    }

    @Operation(summary = "꾸러미 내 질문 순서 변경")
    @PatchMapping("/bundles/{id}/question-order")
    fun updateQuestionOrder(
        @PathVariable id: Long,
        @RequestBody @Valid request: BundleQuestionOrderUpdateRequest,
        @AuthToMember member: Member
    ): ApiResponse<Unit> {
        bundleService.updateQuestionOrder(id, request, member)
        return ApiResponse.noContent()
    }

    @Operation(summary = "선택한 질문 꾸러미에 추가")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/bundles/{id}/questions")
    fun addQuestion(
        @PathVariable id: Long,
        @RequestBody @Valid request: BundleQuestionAddRequest,
        @AuthToMember member: Member
    ): ApiResponse<Unit> {
        bundleService.addQuestion(id, request, member)
        return ApiResponse.created(Unit)
    }

    @Operation(summary = "선택한 질문 꾸러미에서 삭제")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/bundles/{id}/questions")
    fun removeQuestion(
        @PathVariable id: Long,
        @RequestBody @Valid request: BundleQuestionRemoveRequest,
        @AuthToMember member: Member
    ): ApiResponse<Unit> {
        bundleService.removeQuestion(id, request, member)
        return ApiResponse.noContent()
    }
}
