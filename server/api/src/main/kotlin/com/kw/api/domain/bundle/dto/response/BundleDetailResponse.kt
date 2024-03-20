package com.kw.api.domain.bundle.dto.response

import com.kw.api.domain.member.dto.response.SnsResponse
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
    val writer: WriterResponse?,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
) {


    companion object {
        fun of(bundle: Bundle, questions: List<Question>, requestMemberId: Long?): BundleDetailResponse {
            return BundleDetailResponse(
                id = bundle.id!!,
                name = bundle.name,
                shareType = bundle.shareType.name,
                tags = bundle.bundleTags.map { it.tag }.map { TagResponse.from(it) },
                questions = questions.map { QuestionResponse.from(it, requestMemberId) },
                originId = bundle.originId,
                writer = bundle.member?.let { WriterResponse.from(it) },
                createdAt = bundle.createdAt,
                updatedAt = bundle.updatedAt
            )
        }
    }

    data class WriterResponse(
        val id: Long,
        val nickname: String?,
        val profileImage: String?,
        val email: String,
        val snsList: List<SnsResponse>,
        val tags: List<TagResponse>,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
    ) {

        companion object {
            fun from(writer: Member): WriterResponse {
                return WriterResponse(
                    id = writer.id!!,
                    nickname = writer.nickname,
                    profileImage = writer.profileImage,
                    email = writer.email,
                    snsList = writer.snsList.map { SnsResponse.from(it) },
                    tags = writer.memberTags.map { TagResponse.from(it.tag) },
                    createdAt = writer.createdAt,
                    updatedAt = writer.updatedAt
                )
            }
        }
    }
}
