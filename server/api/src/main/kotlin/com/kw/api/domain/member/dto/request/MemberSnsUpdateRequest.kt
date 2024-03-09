package com.kw.api.domain.member.dto.request

data class MemberSnsUpdateRequest(val SnsRequests: List<SnsRequest>) {
    data class SnsRequest(val name: String,
                          val url: String)
}
