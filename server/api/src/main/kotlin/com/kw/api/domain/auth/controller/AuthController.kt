package com.kw.api.domain.auth.controller

import com.kw.api.common.dto.response.ApiResponse
import com.kw.api.domain.auth.dto.request.RefreshTokenRequest
import com.kw.api.domain.auth.dto.response.TokenResponse
import com.kw.api.domain.auth.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "인증", description = "인증 관련 API 입니다.")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(val authService: AuthService) {

    @Operation(summary = "로그아웃을 합니다.")
    @GetMapping("/logout")
    fun logout(@RequestBody refreshTokenRequest: RefreshTokenRequest) {
        authService.logout(refreshTokenRequest)
    }

    @Operation(summary = "리프레시 토큰으로 어세스 토큰을 재발급합니다.")
    @GetMapping("/refresh-access-token")
    fun refreshAccessToken(@RequestBody refreshTokenRequest: RefreshTokenRequest) : ApiResponse<TokenResponse> {
        val response = authService.refreshAccessToken(refreshTokenRequest)
        return ApiResponse.ok(response)
    }
}
