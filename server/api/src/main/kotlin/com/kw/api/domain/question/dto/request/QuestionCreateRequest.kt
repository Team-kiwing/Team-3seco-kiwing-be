package com.kw.api.domain.question.dto.request

import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.member.Member
import com.kw.data.domain.question.Question
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class QuestionCreateRequest(

    @field:NotBlank(message = "내용은 필수입니다.")
    @field:Size(max = 300, message = "내용은 최대 300자까지 입력해 주세요.")
    val content: String,

    @field:Size(max = 1500, message = "답변은 최대 1500자까지 입력해 주세요.")
    val answer: String?,

    val answerShareType: Question.AnswerShareType,

    val tagIds: List<Long>?,

    val bundleId: Long
) {
    fun toEntity(bundle: Bundle, member: Member): Question {
        return Question(
            content = content,
            answer = answer,
            answerShareType = answerShareType,
            bundle = bundle,
            member = member
        )
    }
}
