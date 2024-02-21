package com.kw.api.domain.bundle.dto.request

data class BundleUpdateRequest(
    val name: String?,
    val shareType: String?,
    val tagIds: List<Long>?
)
