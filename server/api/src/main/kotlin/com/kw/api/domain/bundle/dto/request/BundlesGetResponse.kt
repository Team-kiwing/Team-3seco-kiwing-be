package com.kw.api.domain.bundle.dto.request

import com.kw.data.domain.bundle.Bundle

data class BundlesGetResponse(
    val id: Long,
    val name: String,
    val shareType: String,
) {
    companion object {
        fun from(bundle: Bundle): BundlesGetResponse {
            return BundlesGetResponse(
                id = bundle.id!!,
                name = bundle.name,
                shareType = bundle.shareType.name,
            )
        }
    }
}
