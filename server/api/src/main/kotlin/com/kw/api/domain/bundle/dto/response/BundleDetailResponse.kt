package com.kw.api.domain.bundle.dto.response

import com.kw.api.domain.question.dto.response.QuestionResponse
import com.kw.api.domain.tag.dto.response.TagResponse
import com.kw.data.domain.bundle.Bundle
import java.time.LocalDateTime

data class BundleDetailResponse(
    val id: Long,
    val name: String,
    val shareType: String,
    val tags: List<TagResponse>,
    val questions: List<QuestionResponse>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(bundle: Bundle): BundleDetailResponse {
            return BundleDetailResponse(
                id = bundle.id!!,
                name = bundle.name,
                shareType = bundle.shareType.name,
                tags = bundle.bundleTags.map { it.tag }.map { TagResponse.from(it) },
                questions = bundle.questions.map { QuestionResponse.from(it) },
                createdAt = bundle.createdAt,
                updatedAt = bundle.updatedAt
            )
        }
    }
}
