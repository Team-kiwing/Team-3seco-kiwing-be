package com.kw.infrasecurity.resolver

import com.kw.data.domain.member.Member
import com.kw.data.domain.member.repository.MemberRepository
import com.kw.infrasecurity.oauth.OAuth2UserDetails
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
        val authentication = SecurityContextHolder.getContext().authentication ?: throw IllegalArgumentException("접근이 거부되었습니다.")

        val userDetails = authentication.principal
        var email: String = ""

        if(userDetails is DefaultOAuth2User){
            email = userDetails.getAttribute<String>("email").toString()
        }
        else if(userDetails is OAuth2UserDetails) {
            email = userDetails.email
        }

        val member = memberRepository.findMemberByEmail(email) ?: throw IllegalArgumentException("존재하지 않는 회원입니다.")

        return member
    }
}
