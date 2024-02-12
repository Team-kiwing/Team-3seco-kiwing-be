package com.kw.api.bundle.service

import com.kw.api.bundle.dto.request.*
import com.kw.api.bundle.dto.response.BundleCreateResponse
import com.kw.api.bundle.dto.response.BundleGetResponse
import com.kw.api.common.dto.request.PageCondition
import com.kw.api.common.dto.response.PageResponse
import com.kw.data.domain.bundle.repository.BundleRepository
import org.springframework.stereotype.Service

@Service
class BundleService(
    private val bundleRepository: BundleRepository
) {

    fun createBundle(request: BundleCreateRequest): BundleCreateResponse {
    }

    fun getBundles(
        getCondition: BundleGetCondition,
        pageCondition: PageCondition
    ): PageResponse<BundleGetResponse> {
    }

    fun searchBundles(
        searchCondition: BundleSearchCondition,
        pageCondition: PageCondition
    ): PageResponse<BundleGetResponse> {
    }

    fun getBundle(id: Long): BundleGetResponse {
    }

    fun updateBundle(id: Long) {}

    fun deleteBundle(id: Long) {}

    fun addQuestion(id: Long, request: BundleQuestionAddRequest) {}

    fun removeQuestion(id: Long, request: BundleQuestionRemoveRequest) {}

    fun updateQuestionOrderList(id: Long, request: BundleQuestionOrderListUpdateRequest) {}

}
