package com.kw.api.domain.auth.service

import com.kw.api.domain.auth.dto.request.RefreshTokenRequest
import com.kw.api.domain.auth.dto.response.TokenResponse
import com.kw.data.domain.member.Member
import com.kw.data.domain.member.repository.MemberRepository
import com.kw.infraredis.repository.RedisRefreshTokenRepository
import com.kw.infrasecurity.jwt.JwtTokenProvider
import com.kw.infrasecurity.oauth.OAuth2UserDetails
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service

@Service
class AuthService(val redisRefreshTokenRepository: RedisRefreshTokenRepository,
    val memberRepository: MemberRepository,
    val jwtTokenProvider: JwtTokenProvider) {

    fun logout(refreshTokenRequest: RefreshTokenRequest) {
        redisRefreshTokenRepository.delete(refreshTokenRequest.refreshToken)
    }

    fun refreshAccessToken(refreshTokenRequest: RefreshTokenRequest) : TokenResponse {
        val memberId = redisRefreshTokenRepository.findByRefreshToken(refreshTokenRequest.refreshToken) ?: throw RuntimeException("만료된 리프레시 토큰")
        val member = memberRepository.findByIdOrNull(memberId) ?: throw RuntimeException("존재하지 않는 회원")

        val oauth2UserDetails = createOauth2UserDetails(member)
        val accessToken = jwtTokenProvider.generateAccessToken(oauth2UserDetails)
        val refreshToken = jwtTokenProvider.generateRefreshToken()
        redisRefreshTokenRepository.save(refreshToken, memberId)

        return TokenResponse(accessToken, refreshToken)
    }

    private fun createOauth2UserDetails(member: Member): OAuth2UserDetails{
        val authorities: MutableList<SimpleGrantedAuthority> = member.memberRoles
            .stream()
            .map { memberRole -> SimpleGrantedAuthority(memberRole.toString()) }
            .toList()
            .toMutableList()

        return OAuth2UserDetails(id = member.id!!,
            email = member.email,
            authorities = authorities)
    }
}
