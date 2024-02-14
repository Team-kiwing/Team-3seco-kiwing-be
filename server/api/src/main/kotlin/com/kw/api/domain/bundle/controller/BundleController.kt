package com.kw.api.domain.bundle.controller

import com.kw.api.common.dto.response.ApiResponse
import com.kw.api.domain.bundle.dto.request.*
import com.kw.api.domain.bundle.dto.response.BundleGetResponse
import com.kw.api.domain.bundle.service.BundleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

//TODO: 전체적으로 member, shareType 연동
@RestController
@RequestMapping("/api/v1")
class BundleController(
    private val bundleService: BundleService
) {

    @PostMapping("/bundles")
    fun createBundle(@RequestBody request: BundleCreateRequest): ResponseEntity<ApiResponse<BundleGetResponse>> {
        val response: ApiResponse<BundleGetResponse> = ApiResponse.created(bundleService.createBundle(request))
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/bundles/my")
    fun getMyBundles(): ApiResponse<List<BundlesGetResponse>> {
        return ApiResponse.ok(bundleService.getMyBundles())
    }

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

    @DeleteMapping("/bundles/{id}")
    fun deleteBundle(@PathVariable id: Long) {
        return bundleService.deleteBundle(id)
    }

    @PostMapping("/bundles/{id}/questions")
    fun addQuestion(
        @PathVariable id: Long,
        @RequestBody request: BundleQuestionAddRequest
    ): ResponseEntity<Unit> {
        bundleService.addQuestion(id, request)
        return ResponseEntity.status(HttpStatus.CREATED).build()
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
