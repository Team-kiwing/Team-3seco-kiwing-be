package com.kw.api.domain.question.dto.request

import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.question.Question
import jakarta.validation.constraints.NotBlank

data class QuestionCreateRequest(
    @field:NotBlank(message = "내용은 필수입니다.")
    val content: String,

    val answer: String?,

    val answerShareType: Question.AnswerShareType,

    val tagIds: List<Long>?,

    val bundleId: Long
) {
    fun toEntity(bundle: Bundle): Question {
        return Question(
            content = content,
            answer = answer,
            answerShareType = answerShareType,
            bundle = bundle
        )
    }
}
