package com.kw.api.domain.bundle.dto.response

import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.tag.Tag
import java.time.LocalDateTime

data class BundleGetResponse(
    val id: Long,
    val name: String,
    val shareType: String,
    val tags: List<Tag>,
//    val questions: List<Question>, //TODO
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(bundle: Bundle): BundleGetResponse {
            return BundleGetResponse(
                id = bundle.id!!,
                name = bundle.name,
                shareType = bundle.shareType.name,
                tags = bundle.bundleTags.map { it.tag }.toList(),
                createdAt = bundle.createdAt,
                updatedAt = bundle.updatedAt
            )
        }
    }
}
