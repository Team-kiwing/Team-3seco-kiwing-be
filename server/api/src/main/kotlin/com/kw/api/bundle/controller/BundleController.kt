package com.kw.api.bundle.controller

import com.kw.api.bundle.dto.request.BundleCreateRequest
import com.kw.api.bundle.dto.request.BundleUpdateRequest
import com.kw.api.bundle.dto.response.BundleGetResponse
import com.kw.api.bundle.service.BundleService
import com.kw.api.common.dto.response.ApiResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class BundleController(
    private val bundleService: BundleService
) {

    @PostMapping("/bundles")
    fun createBundle(@RequestBody request: BundleCreateRequest): ApiResponse<BundleGetResponse> {
        return ApiResponse.ok(bundleService.createBundle(request))
    }

//    @GetMapping("/bundles/my")
//    fun getMyBundles(): ApiResponse<List<BundleGetResponse>> {
//    }
//
//    @GetMapping("/bundles")
//    fun getBundles(
//        @ModelAttribute getCondition: BundleGetCondition,
//        @ModelAttribute pageCondition: PageCondition
//    ): ApiResponse<PageResponse<BundleGetResponse>> {
//    }
//
//    @GetMapping("/bundles/search")
//    fun searchBundles(
//        @ModelAttribute searchCondition: BundleSearchCondition,
//        @ModelAttribute pageCondition: PageCondition
//    ): ApiResponse<PageResponse<BundleGetResponse>> {
//    }

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

    @PatchMapping("/bundles/{id}")
    fun deleteBundle(@PathVariable id: Long) {
        return bundleService.deleteBundle(id)
    }

//    @PostMapping("/bundles/{id}/questions")
//    fun addQuestion(
//        @PathVariable id: Long,
//        @RequestBody request: BundleQuestionAddRequest
//    ) {
//        return bundleService.addQuestion(id, request)
//    }
//
//    @DeleteMapping("/bundles/{id}/questions")
//    fun removeQuestion(
//        @PathVariable id: Long,
//        @RequestBody request: BundleQuestionRemoveRequest
//    ) {
//        return bundleService.removeQuestion(id, request)
//    }
//
//    @PatchMapping("/bundles/{id}/question-order-list")
//    fun updateQuestionOrderList(
//        @PathVariable id: Long,
//        @RequestBody request: BundleQuestionOrderListUpdateRequest
//    ) {
//        return bundleService.updateQuestionOrderList(id, request)
//    }

}
