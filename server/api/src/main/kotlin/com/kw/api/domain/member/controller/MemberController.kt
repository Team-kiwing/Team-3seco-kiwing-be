package com.kw.api.domain.member.controller

import com.kw.api.domain.member.dto.request.MemberBundleOrderUpdateRequest
import com.kw.api.domain.member.dto.request.MemberInfoUpdateRequest
import com.kw.api.domain.member.dto.request.MemberSnsUpdateRequest
import com.kw.api.domain.member.dto.response.MemberInfoResponse
import com.kw.api.domain.member.service.MemberService
import com.kw.data.domain.member.Member
import com.kw.infrasecurity.resolver.AuthToMember
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
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
        val response = memberService.updateMemberNickname(member, nickname)
        return response
    }

    @Operation(summary = "사용자의 소셜 링크를 변경합니다.")
    @PatchMapping("/me/sns")
    fun updateMemberSns(
        @AuthToMember member: Member,
        @RequestBody memberSnsUpdateRequest: MemberSnsUpdateRequest
    ): MemberInfoResponse {
        val response = memberService.updateMemberSns(member, memberSnsUpdateRequest)
        return response
    }

    @Operation(summary = "사용자의 관심 태그를 변경합니다.")
    @PatchMapping("/me/tags")
    fun updateMemberTags(
        @AuthToMember member: Member,
        @RequestParam tagIds: List<Long>
    ): MemberInfoResponse {
        val response = memberService.updateMemberTags(member, tagIds)
        return response
    }

    @Operation(summary = "사용자 회원가입 시 회원 정보를 설정합니다.")
    @PatchMapping("/me")
    fun updateMemberInfo(
        @AuthToMember member: Member,
        @RequestBody memberInfoUpdateRequest: MemberInfoUpdateRequest
    ): MemberInfoResponse {
        val response = memberService.updateMemberInfo(member, memberInfoUpdateRequest)
        return response
    }

    @Operation(summary = "회원 프로필 사진을 저장합니다.")
    @PatchMapping(value = ["/me/profile-image"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateMemberProfileImage(
        @AuthToMember member: Member,
        @RequestPart(value = "file", required = true) file: MultipartFile
    ): MemberInfoResponse {
        return memberService.updateMemberProfileImage(member, file)
    }

    @Operation(summary = "회원의 꾸러미 순서 변경")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/me/bundle-order")
    fun updateBundleOrder(
        @AuthToMember member: Member,
        @RequestBody request: MemberBundleOrderUpdateRequest
    ) {
        memberService.updateBundleOrder(member, request)
    }


    @Operation(summary = "회원 아이디로 회원 정보를 가져옵니다.")
    @GetMapping("/{id}")
    fun updateMemberProfileImage(@PathVariable id: Long): MemberInfoResponse {
        val response = memberService.getMemberInfoById(id)
        return response
    }

}
