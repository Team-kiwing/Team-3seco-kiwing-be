package com.kw.api.bundle.service

import com.kw.api.bundle.dto.response.BundleCreateResponse
import com.kw.api.bundle.dto.response.BundleGetResponse
import com.kw.api.bundle.dto.response.BundleSearchResponse
import com.kw.data.domain.bundle.repository.BundleRepository
import org.springframework.stereotype.Service

@Service
class BundleService(
    private val bundleRepository: BundleRepository
) {

    fun createBundle(): BundleCreateResponse {}

    fun searchBundles(): BundleSearchResponse {}

    fun getBundle(): BundleGetResponse {}

    fun updateBundle() {}

    fun deleteBundle() {}

    fun addQuestion() {}

    fun removeQuestion() {}

    fun updateQuestionOrderList() {}

}
