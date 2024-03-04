package com.kw.api.domain.question.dto.request

import com.kw.data.domain.question.Question
import jakarta.validation.constraints.Size

data class QuestionUpdateRequest(
    val content: String?,
    val answer: String?,
    val answerShareType: Question.AnswerShareType?,
    
    @field:Size(max = 3, message = "태그는 최대 3개까지 지정 가능합니다.")
    val tagIds: List<Long>?,
)
