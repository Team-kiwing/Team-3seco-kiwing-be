package com.kw.api.domain.auth.controller

import com.kw.api.common.dto.response.ApiResponse
import com.kw.api.domain.auth.dto.request.RefreshTokenRequest
import com.kw.api.domain.auth.dto.response.TokenResponse
import com.kw.api.domain.auth.service.AuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(val authService: AuthService) {

    @GetMapping("/logout")
    fun logout(@RequestBody refreshTokenRequest: RefreshTokenRequest) {
        authService.logout(refreshTokenRequest)
    }

    @GetMapping("/refresh-access-token")
    fun refreshAccessToken(@RequestBody refreshTokenRequest: RefreshTokenRequest) : ApiResponse<TokenResponse> {
        val response = authService.refreshAccessToken(refreshTokenRequest)
        return ApiResponse.ok(response)
    }
}
