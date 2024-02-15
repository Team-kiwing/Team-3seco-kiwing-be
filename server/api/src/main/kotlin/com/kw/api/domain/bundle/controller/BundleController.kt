package com.kw.api.domain.bundle.controller

import com.kw.api.common.dto.request.PageCondition
import com.kw.api.common.dto.response.ApiResponse
import com.kw.api.common.dto.response.PageResponse
import com.kw.api.domain.bundle.dto.request.*
import com.kw.api.domain.bundle.dto.response.BundleGetResponse
import com.kw.api.domain.bundle.service.BundleService
import com.kw.data.domain.bundle.dto.request.BundleGetCondition
import com.kw.data.domain.bundle.dto.request.BundleSearchCondition
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
    fun createBundle(@RequestBody request: BundleCreateRequest): ApiResponse<BundleGetResponse> {
        return ApiResponse.created(bundleService.createBundle(request))
    }

    //TODO: ES
    @GetMapping("/bundles/search")
    fun searchBundles(
        @ModelAttribute searchCondition: BundleSearchCondition,
        @ModelAttribute pageCondition: PageCondition
    ): ApiResponse<PageResponse<BundlesGetResponse>> {
        return ApiResponse.ok(bundleService.searchBundles(searchCondition, pageCondition))
    }

    @GetMapping("/bundles/my")
    fun getMyBundles(
        @ModelAttribute getCondition: BundleGetCondition
    ): ApiResponse<List<BundlesGetResponse>> {
        return ApiResponse.ok(bundleService.getMyBundles(getCondition))
    }

    @GetMapping("/bundles/{id}")
    fun getBundle(@PathVariable id: Long): ApiResponse<BundleGetResponse> {
        return ApiResponse.ok(bundleService.getBundle(id))
    }

    @PatchMapping("/bundles/{id}")
    fun updateBundle(
        @PathVariable id: Long,
        @RequestBody request: BundleUpdateRequest
    ) {
        return bundleService.updateBundle(id, request)
    }

    @DeleteMapping("/bundles/{id}")
    fun deleteBundle(@PathVariable id: Long) {
        return bundleService.deleteBundle(id)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/bundles/{id}/questions")
    fun addQuestion(
        @PathVariable id: Long,
        @RequestBody request: BundleQuestionAddRequest
    ) {
        bundleService.addQuestion(id, request)
    }

    @DeleteMapping("/bundles/{id}/questions")
    fun removeQuestion(
        @PathVariable id: Long,
        @RequestBody request: BundleQuestionRemoveRequest
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
