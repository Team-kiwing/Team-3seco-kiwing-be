package com.kw.api.domain.member.dto.request

data class MemberSnsUpdateRequest(val snsRequests: List<snsRequest>) {
    data class snsRequest(val name: String,
                          val url: String)
}
