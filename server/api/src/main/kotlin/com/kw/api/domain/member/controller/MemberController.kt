package com.kw.api.domain.member.controller

import com.kw.api.common.dto.response.ApiResponse
import com.kw.api.domain.member.dto.response.MemberInfoResponse
import com.kw.api.domain.member.service.MemberService
import com.kw.infrasecurity.oauth.OAuth2UserDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/member")
class MemberController(private val memberService: MemberService) {
    @GetMapping("/me")
    fun getUserInfo(@AuthenticationPrincipal userDetails : OAuth2UserDetails) : ApiResponse<MemberInfoResponse> {
        val response = memberService.getUserInfo(userDetails)
        return ApiResponse.ok(response)
    }
}
