package com.kw.infrasecurity.jwt

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver

@Component
class JwtAccessDeniedHandler() : AccessDeniedHandler {

    private lateinit var resolver: HandlerExceptionResolver

    constructor (@Qualifier("handlerExceptionResolver") resolver: HandlerExceptionResolver) : this() {
        this.resolver = resolver
    }

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        resolver.resolveException(request!!, response!!, null, accessDeniedException!!)
    }
}
