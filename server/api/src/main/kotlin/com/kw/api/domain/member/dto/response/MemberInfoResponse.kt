package com.kw.api.domain.member.dto.response

import com.kw.data.domain.member.Member

data class MemberInfoResponse(val id: Long?,
                              val nickname: String?,
                              val email: String,
                              val provider: Member.Provider,
                              val profileImage: String?,
                              val snsList: List<SnsResponse>
    ) {
    companion object {
        fun from(member: Member) : MemberInfoResponse {
            val snsResponses = member.snsList.map { sns ->
                SnsResponse(
                    name = sns.name,
                    url = sns.url
                )
            }.toList()

            return MemberInfoResponse(
                id = member.id,
                nickname = member.nickname,
                email = member.email,
                provider = member.provider,
                profileImage = member.profileImage,
                snsList = snsResponses
                )
        }
    }

    data class SnsResponse(val name: String,
                           val url: String)
}
