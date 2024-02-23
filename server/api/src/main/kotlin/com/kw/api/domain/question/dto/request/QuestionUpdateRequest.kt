package com.kw.api.domain.question.dto.request

import com.kw.data.domain.question.Question

data class QuestionUpdateRequest(
    val content: String?,
    val answer: String?,
    val answerShareType: Question.AnswerShareType?,
    val tagIds: List<Long>?,
)
