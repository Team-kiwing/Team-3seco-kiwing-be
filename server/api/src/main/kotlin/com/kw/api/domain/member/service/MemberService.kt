package com.kw.api.domain.member.service

import com.kw.api.common.exception.ApiErrorCode
import com.kw.api.common.exception.ApiException
import com.kw.api.domain.member.dto.request.MemberSnsUpdateRequest
import com.kw.api.domain.member.dto.response.MemberInfoResponse
import com.kw.data.domain.member.Member
import com.kw.data.domain.member.MemberTag
import com.kw.data.domain.member.Sns
import com.kw.data.domain.member.repository.MemberRepository
import com.kw.data.domain.tag.repository.TagRepository
import com.kw.infras3.service.S3Service
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class MemberService(private val memberRepository: MemberRepository,
                    private val s3Service: S3Service,
                    private val tagRepository: TagRepository) {

    @Transactional(readOnly = true)
    fun getMemberInfo(member: Member): MemberInfoResponse{
        return MemberInfoResponse.from(member)
    }

    fun updateMemberNickname(member: Member, nickname: String): MemberInfoResponse {
        isNicknameUnique(nickname)
        member.updateMemberNickname(nickname)
        return MemberInfoResponse.from(member)
    }

    fun updateMemberProfileImage(member: Member, file: MultipartFile): MemberInfoResponse {
        val profileImageUrl = s3Service.uploadImage(file)
        member.updateMemberProfileImage(profileImageUrl)
        return MemberInfoResponse.from(member)
    }

    fun updateMemberSns(member: Member, memberSnsUpdateRequest: MemberSnsUpdateRequest): MemberInfoResponse {
        val snsList = memberSnsUpdateRequest.snsRequests.map { snsRequest ->
            Sns(
                name = snsRequest.name,
                url = snsRequest.url,
                member = member
            )
        }.toList()

        member.updateMemberSns(snsList)
        return MemberInfoResponse.from(member)
    }

    fun updateMemberTags(member: Member, tagIds: List<Long>): MemberInfoResponse {
        val memberTags = tagIds.map { tagId ->
            val tag = tagRepository.findByIdOrNull(tagId) ?: throw ApiException(ApiErrorCode.INCLUDE_NOT_FOUND_TAG)
            MemberTag(member, tag)
        }.toList()

        member.updateMemberTags(memberTags)
        return MemberInfoResponse.from(member)
    }

    private fun isNicknameUnique(nickname: String) {
        if(memberRepository.existsByNickname(nickname)){
            throw ApiException(ApiErrorCode.NICKNAME_ALREADY_EXISTS)
        }
    }
}
