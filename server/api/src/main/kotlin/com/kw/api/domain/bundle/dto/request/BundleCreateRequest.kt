package com.kw.api.domain.bundle.dto.request

import com.kw.data.domain.bundle.Bundle
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank

data class BundleCreateRequest(
    @NotBlank(message = "이름은 필수입니다.")
    val name: String,

    @NotBlank(message = "공개 범위 설정은 필수입니다.")
    val shareType: String,

    @Max(value = 3, message = "태그는 최대 3개까지 지정 가능합니다.")
    val tagIds: List<Long>?
) {
    fun toEntity(): Bundle {
        return Bundle(
            name = name,
            shareType = Bundle.ShareType.from(shareType),
        )
    }
}