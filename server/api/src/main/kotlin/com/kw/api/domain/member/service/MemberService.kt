package com.kw.api.domain.member.service

import com.kw.api.common.exception.ApiErrorCode
import com.kw.api.common.exception.ApiException
import com.kw.api.domain.member.dto.response.MemberInfoResponse
import com.kw.data.domain.member.repository.MemberRepository
import com.kw.infrasecurity.oauth.OAuth2UserDetails
import org.springframework.stereotype.Service

@Service
class MemberService(val memberRepository: MemberRepository) {

    fun getMemberInfo(userDetails: OAuth2UserDetails?) : MemberInfoResponse{
        val email = userDetails?.email ?: throw ApiException(ApiErrorCode.ACCESS_DENIED)
        val member = memberRepository.findMemberByEmail(email) ?: throw ApiException(ApiErrorCode.NOT_FOUND_MEMBER)
        return MemberInfoResponse.from(member)
    }
}
