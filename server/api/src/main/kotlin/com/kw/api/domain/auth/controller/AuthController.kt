package com.kw.api.domain.auth.controller

import com.kw.api.domain.auth.dto.request.RefreshTokenRequest
import com.kw.api.domain.auth.dto.response.TokenResponse
import com.kw.api.domain.auth.service.AuthService
import com.kw.data.domain.member.Member
import com.kw.infrasecurity.resolver.AuthToMember
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "인증", description = "인증 관련 API 입니다.")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(val authService: AuthService) {

    @Operation(summary = "로그아웃을 합니다.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/logout")
    fun logout(@RequestBody refreshTokenRequest: RefreshTokenRequest) {
        authService.logout(refreshTokenRequest)
    }

    @Operation(summary = "리프레시 토큰으로 어세스 토큰을 재발급합니다.")
    @PostMapping("/refresh-access-token")
    fun refreshAccessToken(@RequestBody refreshTokenRequest: RefreshTokenRequest): TokenResponse {
        return authService.refreshAccessToken(refreshTokenRequest)
    }

    @Operation(summary = "회원탈퇴합니다.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/withdraw")
    fun withdrawMember(@AuthToMember member: Member) {
        authService.withdrawMember(member)
    }
}
