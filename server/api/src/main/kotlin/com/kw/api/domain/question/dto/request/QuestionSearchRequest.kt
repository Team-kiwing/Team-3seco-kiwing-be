package com.kw.api.domain.question.dto.request

data class QuestionSearchRequest(
    val keyword: String?,
    val page: Long = 1,
    val size: Long = 10
)
