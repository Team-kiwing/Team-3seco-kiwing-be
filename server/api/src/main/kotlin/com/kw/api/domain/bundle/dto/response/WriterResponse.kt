package com.kw.api.domain.bundle.dto.response

import com.kw.api.domain.member.dto.response.SnsResponse
import com.kw.api.domain.tag.dto.response.TagResponse
import com.kw.data.domain.member.Member
import java.time.LocalDateTime

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
