package com.kw.api.domain.bundle.dto.request

import jakarta.validation.constraints.Size

data class BundleQuestionOrderUpdateRequest(
    @field:Size(min = 1, message = "질문의 아이디를 순서대로 입력해 주세요.")
    val questionOrder: List<Long>
)
