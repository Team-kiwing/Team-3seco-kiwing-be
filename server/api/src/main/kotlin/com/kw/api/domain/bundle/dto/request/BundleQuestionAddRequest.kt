package com.kw.api.domain.bundle.dto.request

import jakarta.validation.constraints.Size

data class BundleQuestionAddRequest(
    @field:Size(min = 1, message = "추가할 꾸러미를 선택해 주세요.")
    val bundleIds: List<Long>,

    @field:Size(min = 1, message = "추가할 질문을 선택해 주세요.")
    val questionIds: List<Long>
)
