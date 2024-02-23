package com.kw.api.domain.bundle.dto.request

import com.kw.data.domain.bundle.Bundle

data class BundleUpdateRequest(
    val name: String?,
    val shareType: Bundle.ShareType?,
    val tagIds: List<Long>?
)
