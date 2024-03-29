package com.kw.api.domain.bundle.dto.request

import jakarta.validation.constraints.Size

data class BundleQuestionRemoveRequest(
    @field:Size(min = 1, message = "삭제할 질문을 선택해 주세요.")
    val questionIds: List<Long>
)
