package com.kw.infrasecurity.util

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class HttpResponseUtil {
    data class tokenRespone(val accessToken: String, val refreshToken: String)

    companion object {
        fun writeResponse(response : HttpServletResponse, accessToken : String, refreshToken : String) {
            val objectMapper = ObjectMapper()
            val responseBody: String = objectMapper.writeValueAsString(tokenRespone(accessToken, refreshToken))
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.status = HttpStatus.OK.value()
            response.characterEncoding = "UTF-8"
            response.writer.write(responseBody)
        }
    }
}
