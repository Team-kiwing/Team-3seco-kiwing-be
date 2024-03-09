package com.kw.api.domain.bundle.dto.response

import com.kw.api.domain.tag.dto.response.TagResponse
import com.kw.data.domain.bundle.Bundle
import java.time.LocalDateTime

data class BundleResponse(
    val id: Long,
    val name: String,
    val shareType: String,
    val scrapeCount: Long,
    val tags: List<TagResponse>,
    val isHot: Boolean,
    val originId: Long? = null,
    val writerId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(bundle: Bundle): BundleResponse {
            return BundleResponse(
                id = bundle.id!!,
                name = bundle.name,
                shareType = bundle.shareType.name,
                scrapeCount = bundle.scrapeCount,
                tags = bundle.bundleTags.map { TagResponse.from(it.tag) },
                isHot = bundle.isHot(),
                originId = bundle.originId,
                writerId = bundle.member.id!!,
                createdAt = bundle.createdAt,
                updatedAt = bundle.updatedAt
            )
        }
    }
}
