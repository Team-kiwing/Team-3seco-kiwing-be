package com.kw.infrasecurity.util

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component
import java.security.SignatureException

@Component
class HttpResponseUtil(@Value("\${client-redirect-url}") val REDIRECT_URL: String) {

    private val objectMapper = ObjectMapper()

    fun writeResponse(response : HttpServletResponse, accessToken : String, refreshToken : String, isSignUp : Boolean) {
        var redirectUrl = REDIRECT_URL
        if(isSignUp) {
            redirectUrl += "/register"
        }
        else {
            redirectUrl += "/auth"
        }
        val sb = StringBuffer(redirectUrl)
        sb.append("?").append("access-token=").append(accessToken)
        sb.append("&").append("refresh-token=").append(refreshToken)

        response.sendRedirect(sb.toString())
    }

    fun writeErrorResponse(response : HttpServletResponse, httpStatus: HttpStatus, exception: Exception) {
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = httpStatus.value()
        response.characterEncoding = "UTF-8"
        val errorResponse = ErrorResponse.of(exception)
        objectMapper.writeValue(response.outputStream, errorResponse)
    }

    data class ErrorResponse(
        val code: String,
        val message: String
    ) {
        companion object {
            fun of(e: Exception): ErrorResponse {
                return when (e) {
                    is AccessDeniedException -> ErrorResponse("ACCESS_DENIED", "접근이 거부되었습니다.")
                    is SignatureException -> ErrorResponse("ACCESS_TOKEN_MALFORMED", "올바르지 않은 토큰입니다.")
                    is MalformedJwtException -> ErrorResponse("ACCESS_TOKEN_MALFORMED", "올바르지 않은 토큰입니다.")
                    is ExpiredJwtException -> ErrorResponse("ACCESS_TOKEN_EXPIRED", "어세스 토큰이 만료되었으니 재발급 해주세요")

                    else -> ErrorResponse("FORBIDDEN", "권한이 없습니다.")
                }
            }
        }
    }
}
