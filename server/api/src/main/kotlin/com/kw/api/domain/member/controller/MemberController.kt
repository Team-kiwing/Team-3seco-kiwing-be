package com.kw.api.domain.member.controller

import com.kw.api.common.dto.response.ApiResponse
import com.kw.api.domain.member.dto.response.MemberInfoResponse
import com.kw.api.domain.member.service.MemberService
import com.kw.data.domain.member.Member
import com.kw.infrasecurity.resolver.AuthToMember
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원", description = "회원 관련 API 입니다.")
@RestController
@RequestMapping("/api/v1/members")
class MemberController(private val memberService: MemberService) {

    @Operation(summary = "로그인된 사용자의 정보를 가져옵니다.")
    @GetMapping("/me")
    fun getUserInfo(@AuthToMember member: Member) : ApiResponse<MemberInfoResponse> {
        val response = memberService.getMemberInfo(member)
        return ApiResponse.ok(response)
    }
}
