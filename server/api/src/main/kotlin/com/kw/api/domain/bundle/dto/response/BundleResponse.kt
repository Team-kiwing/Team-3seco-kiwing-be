package com.kw.api.domain.bundle.dto.response

import com.kw.data.domain.bundle.Bundle
import java.time.LocalDateTime

data class BundleResponse(
    val id: Long,
    val name: String,
    val shareType: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(bundle: Bundle): BundleResponse {
            return BundleResponse(
                id = bundle.id!!,
                name = bundle.name,
                shareType = bundle.shareType.name,
                createdAt = bundle.createdAt,
                updatedAt = bundle.updatedAt
            )
        }
    }
}
