package com.kw.infrasecurity.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(private val tokenProvider: JwtTokenProvider) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val accessToken = resolveToken(request)
        if (accessToken != null) {
            try {
                tokenProvider.validateAccessToken(accessToken)
                val authentication = tokenProvider.getAuthentication(accessToken)
                SecurityContextHolder.getContext().authentication = authentication
            } catch (e: Exception) {
                request.setAttribute("exception", e)
            }
        } else {
            request.setAttribute("exception", AccessDeniedException("요청이 거부되었습니다"))
        }
        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val authorizationHeader = request.getHeader("Authorization")
        return if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer")) {
            authorizationHeader.substring(7)
        } else null
    }
}
