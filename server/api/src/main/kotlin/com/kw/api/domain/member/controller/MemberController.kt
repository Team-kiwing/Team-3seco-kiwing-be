package com.kw.api.domain.member.controller

import com.kw.api.common.dto.response.ApiResponse
import com.kw.api.domain.member.dto.request.MemberSnsUpdateRequest
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
    fun getUserInfo(@AuthToMember member: Member): ApiResponse<MemberInfoResponse> {
        val response = memberService.getMemberInfo(member)
        return ApiResponse.ok(response)
    }

    @Operation(summary = "사용자의 닉네임을 변경합니다.")
    @PatchMapping("/me/nickname")
    fun updateMemberNickname(@AuthToMember member: Member,
                             @RequestParam nickname: String): ApiResponse<MemberInfoResponse> {
        val response = memberService.updateMemberNickname(member, nickname)
        return ApiResponse.ok(response)
    }

    @Operation(summary = "사용자의 소셜 링크를 변경합니다.")
    @PatchMapping("/me/sns")
    fun updateMemberSns(@AuthToMember member: Member,
                        @RequestBody memberSnsUpdateRequest: MemberSnsUpdateRequest): ApiResponse<MemberInfoResponse> {
        val response = memberService.updateMemberSns(member, memberSnsUpdateRequest)
        return ApiResponse.ok(response)
    }

    @Operation(summary = "사용자의 관심 태그를 변경합니다.")
    @PatchMapping("/me/tags")
    fun updateMemberTags(@AuthToMember member: Member,
                        @RequestParam tagIds: List<Long>): ApiResponse<MemberInfoResponse> {
        val response = memberService.updateMemberTags(member, tagIds)
        return ApiResponse.ok(response)
    }


    @Operation(summary = "회원 프로필 사진을 저장합니다.")
    @PatchMapping(value = ["/me/profile-image"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateMemberProfileImage(@AuthToMember member: Member,
                                @RequestPart(value = "file", required = true) file: MultipartFile): ApiResponse<MemberInfoResponse> {
        val response = memberService.updateMemberProfileImage(member, file)
        return ApiResponse.ok(response)
    }
}
