package com.kw.api.domain.bundle.dto.request

import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.member.Member
import jakarta.validation.constraints.NotBlank

data class BundleCreateRequest(
    @field:NotBlank(message = "이름은 필수입니다.")
    val name: String,

    val shareType: Bundle.ShareType,

    val tagIds: List<Long>?
) {
    fun toEntity(member: Member): Bundle {
        return Bundle(
            name = name,
            shareType = shareType,
            member = member
        )
    }
}
