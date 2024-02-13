package com.kw.api.domain.bundle.dto.request

data class BundleQuestionRemoveRequest(
    val bundleId: Long,
    val questionIds: List<Long>,
)
