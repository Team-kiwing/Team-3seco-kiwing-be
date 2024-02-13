package com.kw.api.domain.bundle.dto.request

data class BundleQuestionAddRequest(
    val bundleId: Long,
    val questionIds: List<Long>,
)
