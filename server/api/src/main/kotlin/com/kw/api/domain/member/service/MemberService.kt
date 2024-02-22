package com.kw.api.domain.member.service

import com.kw.api.domain.member.dto.response.MemberInfoResponse
import com.kw.data.domain.member.repository.MemberRepository
import com.kw.infraredis.repository.RedisRefreshTokenRepository
import com.kw.infrasecurity.oauth.OAuth2UserDetails
import org.springframework.stereotype.Service

@Service
class MemberService(val memberRepository: MemberRepository,
    val redisRefreshTokenRepository: RedisRefreshTokenRepository) {

    fun getUserInfo(userDetails: OAuth2UserDetails) : MemberInfoResponse{
        val email = userDetails.email
        val member = memberRepository.findMemberByEmail(email) ?: throw RuntimeException("존재하지 않는 회원입니다")
        return MemberInfoResponse.from(member)
    }
}
