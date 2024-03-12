package com.kw.infrasecurity.jwt

import com.kw.infrasecurity.util.HttpResponseUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver

@Component
class JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") val resolver: HandlerExceptionResolver,
                                  val httpResponseUtil: HttpResponseUtil) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        httpResponseUtil.writeErrorResponse(response, HttpStatus.UNAUTHORIZED, request.getAttribute("exception") as Exception)
    }
}
