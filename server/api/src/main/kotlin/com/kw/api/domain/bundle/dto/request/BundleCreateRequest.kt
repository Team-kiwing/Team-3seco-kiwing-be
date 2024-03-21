package com.kw.api.domain.bundle.dto.request

import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.member.Member
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class BundleCreateRequest(

    @field:NotBlank(message = "이름은 필수입니다.")
    @field:Size(min = 2, max = 100, message = "이름은 최소 2자, 최대 100자까지 입력해 주세요.")
    val name: String,

    val shareType: Bundle.ShareType,

    @field:Size(max = 3, message = "태그는 최대 3개까지 지정 가능합니다.")
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
