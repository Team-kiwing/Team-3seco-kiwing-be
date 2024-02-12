package com.kw.api.bundle.controller

import com.kw.api.bundle.dto.request.*
import com.kw.api.bundle.dto.response.BundleCreateResponse
import com.kw.api.bundle.dto.response.BundleGetResponse
import com.kw.api.bundle.service.BundleService
import com.kw.api.common.dto.request.PageCondition
import com.kw.api.common.dto.response.ApiResponse
import com.kw.api.common.dto.response.PageResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/bundles")
class BundleController(
    private val bundleService: BundleService
) {

    @PostMapping
    fun createBundle(@RequestBody request: BundleCreateRequest): ApiResponse<BundleCreateResponse> {
    }

    @GetMapping
    fun getBundles(
        @ModelAttribute getCondition: BundleGetCondition,
        @ModelAttribute pageCondition: PageCondition
    ): ApiResponse<PageResponse<BundleGetResponse>> {
    }

    @GetMapping("/search")
    fun searchBundles(
        @ModelAttribute searchCondition: BundleSearchCondition,
        @ModelAttribute pageCondition: PageCondition
    ): ApiResponse<PageResponse<BundleGetResponse>> {
    }

    @GetMapping("/{id}")
    fun getBundle(@PathVariable id: Long): ApiResponse<BundleGetResponse> {
    }

    @PatchMapping("/{id}")
    fun updateBundle(
        @PathVariable id: Long,
        @RequestBody request: BundleUpdateRequest
    ) {
        return bundleService.updateBundle(id)
    }

    @PatchMapping("/{id}")
    fun deleteBundle(@PathVariable id: Long) {
        return bundleService.deleteBundle(id)
    }

    @PostMapping("/{id}/questions")
    fun addQuestion(
        @PathVariable id: Long,
        @RequestBody request: BundleQuestionAddRequest
    ) {
        return bundleService.addQuestion(id, request)
    }

    @DeleteMapping("/{id}/questions")
    fun removeQuestion(
        @PathVariable id: Long,
        @RequestBody request: BundleQuestionRemoveRequest
    ) {
        return bundleService.removeQuestion(id, request)
    }

    @PatchMapping("/{id}/question-order-list")
    fun updateQuestionOrderList(
        @PathVariable id: Long,
        @RequestBody request: BundleQuestionOrderListUpdateRequest
    ) {
        return bundleService.updateQuestionOrderList(id, request)
    }

}
