package com.kw.infrasecurity.jwt

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver

@Component
class JwtAuthenticationEntryPoint() : AuthenticationEntryPoint {

    private lateinit var resolver: HandlerExceptionResolver

    constructor (@Qualifier("handlerExceptionResolver") resolver: HandlerExceptionResolver) : this() {
        this.resolver = resolver
    }

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        resolver.resolveException(
            request!!, response!!, null,
            (request.getAttribute("exception") as Exception)
        )
    }
}
