package com.kw.api.domain.member.service

import com.kw.api.domain.member.dto.response.MemberInfoResponse
import com.kw.data.domain.member.Member
import com.kw.data.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(val memberRepository: MemberRepository) {

    fun getMemberInfo(member: Member) : MemberInfoResponse{
        return MemberInfoResponse.from(member)
    }
}
