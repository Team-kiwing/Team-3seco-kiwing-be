package com.kw.api.domain.member.dto.response

import com.kw.data.domain.member.Member

data class MemberInfoResponse(val id: Long?,
                              val nickname: String?,
                              val email: String,
                              val provider: Member.Provider) {
    companion object {
        fun from(member: Member) : MemberInfoResponse {
            return MemberInfoResponse(
                id = member.id,
                nickname = member.nickname,
                email = member.email,
                provider = member.provider)
        }
    }
}
