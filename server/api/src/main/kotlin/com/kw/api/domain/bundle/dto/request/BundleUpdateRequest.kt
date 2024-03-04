package com.kw.api.domain.bundle.dto.request

import com.kw.data.domain.bundle.Bundle
import jakarta.validation.constraints.Size

data class BundleUpdateRequest(
    @field:Size(max = 100, message = "이름은 최대 5자까지 입력해 주세요.")
    val name: String?,
    val shareType: Bundle.ShareType?,
    val tagIds: List<Long>?
)
