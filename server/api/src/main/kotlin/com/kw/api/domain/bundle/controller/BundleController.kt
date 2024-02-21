package com.kw.api.domain.bundle.controller

import com.kw.api.common.dto.request.PageCondition
import com.kw.api.common.dto.response.ApiResponse
import com.kw.api.common.dto.response.PageResponse
import com.kw.api.domain.bundle.dto.request.BundleCreateRequest
import com.kw.api.domain.bundle.dto.request.BundleQuestionAddRequest
import com.kw.api.domain.bundle.dto.request.BundleQuestionRemoveRequest
import com.kw.api.domain.bundle.dto.request.BundleUpdateRequest
import com.kw.api.domain.bundle.dto.response.BundleDetailResponse
import com.kw.api.domain.bundle.dto.response.BundleResponse
import com.kw.api.domain.bundle.service.BundleService
import com.kw.data.domain.bundle.dto.request.BundleGetCondition
import com.kw.data.domain.bundle.dto.request.BundleSearchCondition
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

//TODO: 전체적으로 member, shareType 연동
@RestController
@RequestMapping("/api/v1")
class BundleController(
    private val bundleService: BundleService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/bundles")
    fun createBundle(@RequestBody @Valid request: BundleCreateRequest): ApiResponse<BundleDetailResponse> {
        return ApiResponse.created(bundleService.createBundle(request))
    }

    //TODO: ES
    @GetMapping("/bundles/search")
    fun searchBundles(
        @ModelAttribute @Valid searchCondition: BundleSearchCondition,
        @ModelAttribute @Valid pageCondition: PageCondition
    ): ApiResponse<PageResponse<BundleResponse>> {
        return ApiResponse.ok(bundleService.searchBundles(searchCondition, pageCondition))
    }

    @GetMapping("/bundles/my")
    fun getMyBundles(
        @ModelAttribute @Valid getCondition: BundleGetCondition
    ): ApiResponse<List<BundleResponse>> {
        return ApiResponse.ok(bundleService.getMyBundles(getCondition))
    }

    @GetMapping("/bundles/{id}")
    fun getBundle(
        @PathVariable id: Long,
        @RequestParam("showOnlyMyQuestions", required = false, defaultValue = "false") showOnlyMyQuestions: Boolean,
    ): ApiResponse<BundleDetailResponse> {
        return ApiResponse.ok(bundleService.getBundle(id, showOnlyMyQuestions))
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/bundles/{id}")
    fun updateBundle(
        @PathVariable id: Long,
        @RequestBody @Valid request: BundleUpdateRequest
    ): ApiResponse<BundleResponse> {
        return ApiResponse.ok(bundleService.updateBundle(id, request))
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/bundles/{id}")
    fun deleteBundle(@PathVariable id: Long) {
        return bundleService.deleteBundle(id)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/bundles/{id}/scrape")
    fun scrapeBundle(@PathVariable id: Long) {
        return bundleService.scrapeBundle(id)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/bundles/{id}/questions")
    fun addQuestion(
        @PathVariable id: Long,
        @RequestBody @Valid request: BundleQuestionAddRequest
    ) {
        bundleService.addQuestion(id, request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/bundles/{id}/questions")
    fun removeQuestion(
        @PathVariable id: Long,
        @RequestBody @Valid request: BundleQuestionRemoveRequest
    ) {
        return bundleService.removeQuestion(id, request)
    }

    //TODO
//    @PatchMapping("/bundles/{id}/question-order-list")
//    fun updateQuestionOrderList(
//        @PathVariable id: Long,
//        @RequestBody request: BundleQuestionOrderListUpdateRequest
//    ) {
//        return bundleService.updateQuestionOrderList(id, request)
//    }

}
