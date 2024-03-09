package com.kw.api.domain.question.dto.request

import com.kw.data.domain.question.Question
import jakarta.validation.constraints.Size

data class QuestionUpdateRequest(

    @field:Size(max = 300, message = "내용은 최대 300자까지 입력해 주세요.")
    val content: String?,

    @field:Size(max = 1500, message = "답변은 최대 1500자까지 입력해 주세요.")
    val answer: String?,

    val answerShareType: Question.AnswerShareType?,

    @field:Size(max = 3, message = "태그는 최대 3개까지 지정 가능합니다.")
    val tagIds: List<Long>?,
)
