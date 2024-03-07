package com.kw.api.domain.bundle.controller

import com.kw.api.common.dto.request.PageCondition
import com.kw.api.common.dto.response.PageResponse
import com.kw.api.domain.bundle.dto.request.BundleCreateRequest
import com.kw.api.domain.bundle.dto.request.BundleQuestionAddRequest
import com.kw.api.domain.bundle.dto.request.BundleQuestionRemoveRequest
import com.kw.api.domain.bundle.dto.request.BundleUpdateRequest
import com.kw.api.domain.bundle.dto.response.BundleDetailResponse
import com.kw.api.domain.bundle.dto.response.BundleResponse
import com.kw.api.domain.bundle.service.BundleService
import com.kw.api.domain.member.dto.request.BundleOrderUpdateRequest
import com.kw.data.domain.bundle.dto.request.BundleGetCondition
import com.kw.data.domain.bundle.dto.request.BundleSearchCondition
import com.kw.data.domain.member.Member
import com.kw.infrasecurity.resolver.AuthToMember
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
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
    ): BundleDetailResponse {
        return bundleService.createBundle(request, member)
    }

    //TODO: ES
    @Operation(summary = "꾸러미 검색")
    @GetMapping("/bundles/search")
    fun searchBundles(
        @ParameterObject @ModelAttribute @Valid searchCondition: BundleSearchCondition,
        @ParameterObject @ModelAttribute pageCondition: PageCondition
    ): PageResponse<BundleResponse> {
        return bundleService.searchBundles(searchCondition, pageCondition)
    }

    @Operation(summary = "내 꾸러미 목록 조회")
    @GetMapping("/bundles/my")
    fun getMyBundles(
        @ParameterObject @ModelAttribute @Valid getCondition: BundleGetCondition,
        @AuthToMember member: Member
    ): List<BundleResponse> {
        return bundleService.getMyBundles(getCondition, member)
    }

    @Operation(summary = "꾸러미 상세 조회")
    @GetMapping("/bundles/{id}")
    fun getBundle(
        @PathVariable id: Long,
        @RequestParam("showOnlyMyQuestions", required = false, defaultValue = "false") showOnlyMyQuestions: Boolean,
        @AuthToMember member: Member
    ): BundleDetailResponse {
        return bundleService.getBundle(id, showOnlyMyQuestions, member)
    }

    @Operation(summary = "꾸러미 수정")
    @PatchMapping("/bundles/{id}")
    fun updateBundle(
        @PathVariable id: Long,
        @RequestBody @Valid request: BundleUpdateRequest,
        @AuthToMember member: Member
    ): BundleResponse {
        return bundleService.updateBundle(id, request, member)
    }

    @Operation(summary = "꾸러미 순서 변경")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/bundles/bundle-order")
    fun updateBundleOrder(
        @AuthToMember member: Member,
        @RequestBody request: BundleOrderUpdateRequest
    ) {
        bundleService.updateBundleOrder(member, request)
    }

    @Operation(summary = "꾸러미 삭제")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/bundles/{id}")
    fun deleteBundle(
        @PathVariable id: Long,
        @AuthToMember member: Member
    ) {
        bundleService.deleteBundle(id, member)
    }

    @Operation(summary = "꾸러미 스크랩")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/bundles/{id}/scrape")
    fun scrapeBundle(
        @PathVariable id: Long,
        @AuthToMember member: Member
    ) {
        bundleService.scrapeBundle(id, member)
    }

    @Operation(summary = "선택한 질문 여러 꾸러미에 추가")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/bundles/questions")
    fun addQuestion(
        @RequestBody @Valid request: BundleQuestionAddRequest,
        @AuthToMember member: Member
    ) {
        bundleService.addQuestion(request, member)
    }

    @Operation(summary = "선택한 질문 꾸러미에서 삭제")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/bundles/{id}/questions")
    fun removeQuestion(
        @PathVariable id: Long,
        @RequestBody @Valid request: BundleQuestionRemoveRequest,
        @AuthToMember member: Member
    ) {
        bundleService.removeQuestion(id, request, member)
    }
}
