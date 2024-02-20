package com.kw.api.domain.question.dto.request

import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.question.Question
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class QuestionCreateRequest(
    @NotBlank(message = "내용은 필수입니다.")
    val content: String,

    val answer: String?,

    @NotBlank(message = "답변 공개 범위 설정은 필수입니다.")
    val answerShareStatus: String,

    val tagIds: List<Long>?,

    @NotNull(message = "꾸러미 선택은 필수입니다.")
    val bundleId: Long
) {
    fun toEntity(bundle: Bundle): Question {
        return Question(
            content = content,
            answer = answer,
            answerShareType = Question.AnswerShareType.from(answerShareStatus),
            bundle = bundle
        )
    }
}
