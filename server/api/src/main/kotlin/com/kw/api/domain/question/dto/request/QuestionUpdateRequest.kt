package com.kw.api.domain.question.dto.request

data class QuestionUpdateRequest(
    val content: String?,
    val answer: String?,
    val answerShareStatus: String?,
    val tagIds: List<Long>?,
)
