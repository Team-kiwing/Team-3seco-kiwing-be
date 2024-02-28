package com.kw.api.domain.member.service

import com.kw.api.common.exception.ApiErrorCode
import com.kw.api.common.exception.ApiException
import com.kw.api.domain.member.dto.response.MemberInfoResponse
import com.kw.data.domain.member.Member
import com.kw.data.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(val memberRepository: MemberRepository) {

    fun getMemberInfo(member: Member): MemberInfoResponse{
        return MemberInfoResponse.from(member)
    }

    fun updateMemberNickname(member: Member, nickname: String): MemberInfoResponse {
        isNicknameUnique(nickname)
        member.updateMemberNickname(nickname)
        return MemberInfoResponse.from(member)
    }

    fun withdrawMember(member: Member) {
        member.withdrawMember()
    }

    private fun isNicknameUnique(nickname: String) {
        if(!memberRepository.existsByNickname(nickname)){
            throw ApiException(ApiErrorCode.NICKNAME_ALREADY_EXISTS)
        }

    }
}
