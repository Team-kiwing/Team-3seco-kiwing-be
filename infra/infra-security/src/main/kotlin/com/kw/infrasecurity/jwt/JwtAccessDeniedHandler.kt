package com.kw.infrasecurity.jwt

import com.kw.infrasecurity.util.HttpResponseUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver

@Component
class JwtAccessDeniedHandler(@Qualifier("handlerExceptionResolver") val resolver: HandlerExceptionResolver,
                             val httpResponseUtil: HttpResponseUtil) : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException?
    ) {
        httpResponseUtil.writeErrorResponse(response, HttpStatus.FORBIDDEN, request.getAttribute("exception") as Exception)
    }
}
