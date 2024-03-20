package com.kw.api.domain.bundle.dto.response

import com.kw.api.domain.question.dto.response.QuestionResponse
import com.kw.api.domain.tag.dto.response.TagResponse
import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.member.Member
import com.kw.data.domain.question.Question
import java.time.LocalDateTime

data class BundleDetailResponse(
    val id: Long,
    val name: String? = null,
    val shareType: String,
    val tags: List<TagResponse>? = null,
    val questions: List<QuestionResponse>? = null,
    val originId: Long? = null,
    val writer: Member?,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
) {
    companion object {
        fun of(bundle: Bundle, questions: List<Question>, memberId: Long?): BundleDetailResponse {
            return BundleDetailResponse(
                id = bundle.id!!,
                name = bundle.name,
                shareType = bundle.shareType.name,
                tags = bundle.bundleTags.map { it.tag }.map { TagResponse.from(it) },
                questions = questions.map { QuestionResponse.from(it, memberId) },
                originId = bundle.originId,
                writer = bundle.member,
                createdAt = bundle.createdAt,
                updatedAt = bundle.updatedAt
            )
        }
    }
}
