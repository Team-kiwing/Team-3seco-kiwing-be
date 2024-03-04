package com.kw.api.domain.member.controller

import com.kw.api.domain.member.dto.response.MemberInfoResponse
import com.kw.api.domain.member.service.MemberService
import com.kw.data.domain.member.Member
import com.kw.infrasecurity.resolver.AuthToMember
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "회원", description = "회원 관련 API 입니다.")
@RestController
@RequestMapping("/api/v1/members")
class MemberController(private val memberService: MemberService) {

    @Operation(summary = "로그인된 사용자의 정보를 가져옵니다.")
    @GetMapping("/me")
    fun getUserInfo(@AuthToMember member: Member): MemberInfoResponse {
        return memberService.getMemberInfo(member)
    }

    @Operation(summary = "사용자의 닉네임을 변경합니다.")
    @PatchMapping("/me/nickname")
    fun updateMemberNickname(
        @AuthToMember member: Member,
        @RequestParam nickname: String
    ): MemberInfoResponse {
        return memberService.updateMemberNickname(member, nickname)
    }

    @Operation(summary = "회원 프로필 사진을 저장합니다.")
    @PatchMapping(value = ["/me/profile-image"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateMemberProfileImage(
        @AuthToMember member: Member,
        @RequestPart(value = "file", required = true) file: MultipartFile
    ): MemberInfoResponse {
        return memberService.updateMemberProfileImage(member, file)
    }
}
