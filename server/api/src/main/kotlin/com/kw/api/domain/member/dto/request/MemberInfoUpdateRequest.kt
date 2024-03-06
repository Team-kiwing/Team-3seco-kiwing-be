package com.kw.api.domain.member.dto.request

data class MemberInfoUpdateRequest(val nickname: String,
                                   val snsRequests: List<MemberSnsUpdateRequest.SnsRequest>,
                                   val tagIds: List<Long>) {
}
