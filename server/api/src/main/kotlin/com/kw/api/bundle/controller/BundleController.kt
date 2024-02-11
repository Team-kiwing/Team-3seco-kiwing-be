package com.kw.api.bundle.controller

import com.kw.api.bundle.dto.response.BundleCreateResponse
import com.kw.api.bundle.dto.response.BundleGetResponse
import com.kw.api.bundle.dto.response.BundleSearchResponse
import com.kw.api.bundle.service.BundleService
import com.kw.api.common.dto.ApiResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/bundles")
class BundleController(
    private val bundleService: BundleService
) {

    @PostMapping
    fun createBundle(): ApiResponse<BundleCreateResponse> {
        return bundleService.createBundle()
    }

    @GetMapping
    fun searchBundles(): ApiResponse<BundleSearchResponse> {
        return bundleService.searchBundles()
    }

    @GetMapping("/{id}")
    fun getBundle(): ApiResponse<BundleGetResponse> {
        return bundleService.getBundle()
    }

    @PatchMapping("/{id}")
    fun updateBundle() {
        return bundleService.updateBundle()
    }

    @PatchMapping("/{id}")
    fun deleteBundle() {
        return bundleService.deleteBundle()
    }

    @PostMapping("/{id}/questions")
    fun addQuestion() {
        return bundleService.addQuestion()
    }

    @DeleteMapping("/{id}/questions")
    fun removeQuestion() {
        return bundleService.removeQuestion()
    }

    @PatchMapping("/{id}/question-order-list")
    fun updateQuestionOrderList() {
        return bundleService.updateQuestionOrderList()
    }

}
