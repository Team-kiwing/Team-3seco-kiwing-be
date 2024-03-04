package com.kw.api.domain.member.dto.response

import com.kw.api.domain.tag.dto.response.TagResponse
import com.kw.data.domain.member.Member

data class MemberInfoResponse(val id: Long?,
                              val nickname: String?,
                              val email: String,
                              val provider: Member.Provider,
                              val profileImage: String?,
                              val snsList: List<SnsResponse>,
                              val memberTags: List<TagResponse>
    ) {
    companion object {
        fun from(member: Member) : MemberInfoResponse {
            val snsResponses = member.snsList.map { sns ->
                SnsResponse(
                    name = sns.name,
                    url = sns.url
                )
            }.toList()

            val tagResponses = member.memberTags.map { memberTag ->
                TagResponse(
                    id = memberTag.tag.id,
                    name = memberTag.tag.name
                )
            }.toList()

            return MemberInfoResponse(
                id = member.id,
                nickname = member.nickname,
                email = member.email,
                provider = member.provider,
                profileImage = member.profileImage,
                snsList = snsResponses,
                memberTags = tagResponses
                )
        }
    }

    data class SnsResponse(val name: String,
                           val url: String)
}
