package com.kw.infrasecurity.resolver

import com.kw.data.domain.member.Member
import com.kw.data.domain.member.repository.MemberRepository
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
@Transactional
class AuthToMemberArgumentResolver(val memberRepository: MemberRepository) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        val hasParameterAnnotation = parameter.hasParameterAnnotation(AuthToMember::class.java)
        val hasLongParameterType = parameter.parameterType.isAssignableFrom(Member::class.java)
        return hasParameterAnnotation && hasLongParameterType
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val authentication =
            SecurityContextHolder.getContext().authentication ?: throw IllegalArgumentException("접근이 거부되었습니다.")
        val userDetails = authentication.principal as DefaultOAuth2User
        val member = memberRepository.findMemberByEmail(userDetails.getAttribute<String>("email").toString())
            ?: throw IllegalArgumentException("존재하지 않는 회원입니다.")

        isMemberWithdraw(member)

        return member
    }

    private fun isMemberWithdraw(member: Member) {
        if (member.deletedAt != null) {
            throw IllegalArgumentException("탈퇴한 회원입니다.")
        }
    }
}
