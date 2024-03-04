package com.kw.infrasecurity.oauth

import com.kw.data.domain.member.Member
import com.kw.data.domain.member.repository.MemberRepository
import com.kw.infraredis.repository.RedisRefreshTokenRepository
import com.kw.infrasecurity.jwt.JwtTokenProvider
import com.kw.infrasecurity.util.HttpResponseUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class OAuth2SuccessHandler(private val jwtTokenProvider: JwtTokenProvider,
    private val memberRepository: MemberRepository,
    private val redisRefreshTokenRepository: RedisRefreshTokenRepository,
    private val httpResponseUtil: HttpResponseUtil) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val principal = authentication!!.principal as DefaultOAuth2User
        val email = principal.getAttribute<String>("email")

        var member = Member(email = email!!)
        var isSignUp = false
        if(isMember(email)){
            member = getMember(email)
        }
        else {
            member = createMember(member)
            isSignUp = true
        }
        val authorities = member.memberRoles.map { memberRole ->
            SimpleGrantedAuthority(memberRole.toString())
        }.toMutableList()

        val oAuth2UserDetails = OAuth2UserDetails(
            id = member.id!!,
            email = email,
            authorities = authorities
        )

        val accessToken = jwtTokenProvider.generateAccessToken(oAuth2UserDetails)
        val refreshToken = jwtTokenProvider.generateRefreshToken()

        redisRefreshTokenRepository.save(refreshToken = refreshToken, memberId = member.id!!)

        httpResponseUtil.writeResponse(response!!, accessToken, refreshToken, isSignUp)
    }

    private fun getMember(email : String) : Member {
        return memberRepository.findMemberByEmail(email) ?: throw RuntimeException("회원이 존재하지 않습니다")
    }

    private fun createMember(member : Member) : Member {
        return memberRepository.save(member)
    }

    private fun isMember(email : String) : Boolean {
        return memberRepository.findMemberByEmail(email) != null
    }
}
