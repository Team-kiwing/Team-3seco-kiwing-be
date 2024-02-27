package com.kw.infrasecurity.resolver

import com.kw.data.domain.member.Member
import com.kw.data.domain.member.repository.MemberRepository
import com.kw.infrasecurity.oauth.OAuth2UserDetails
import org.springframework.core.MethodParameter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
@Transactional
class AuthToMemberArgumentResolver(val memberRepository: MemberRepository): HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        val hasParameterAnnotation = parameter.hasParameterAnnotation(AuthToMember::class.java)
        val hasLongParameterType = parameter.parameterType.isAssignableFrom(Long::class.java)
        return hasParameterAnnotation && hasLongParameterType
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val authentication = SecurityContextHolder.getContext().authentication ?: throw RuntimeException("forbidden")
        val userDetails = authentication.principal as OAuth2UserDetails
        val member = memberRepository.findByIdOrNull(userDetails.id) ?: throw RuntimeException("존재하지 않는 회원")

        isMemberWithdraw(member)

        return member
    }

    private fun isMemberWithdraw(member: Member) {
        if (member.deletedAt != null) {
            throw RuntimeException("이미 탈퇴한 회원")
        }
    }
}
