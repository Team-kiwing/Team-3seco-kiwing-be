package com.kw.api.domain.member.dto.response

import com.kw.data.domain.member.Sns

data class SnsResponse(
    val name: String,
    val url: String
) {

    companion object {
        fun from(sns: Sns): SnsResponse {
            return SnsResponse(sns.name, sns.url)
        }
    }
}
