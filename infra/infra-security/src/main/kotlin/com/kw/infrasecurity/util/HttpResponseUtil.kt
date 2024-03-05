package com.kw.infrasecurity.util

import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class HttpResponseUtil(@Value("\${client-redirect-url}") val REDIRECT_URL: String) {

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
}
