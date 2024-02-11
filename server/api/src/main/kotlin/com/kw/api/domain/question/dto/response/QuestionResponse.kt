package com.kw.api.domain.question.dto.response

import com.kw.data.domain.question.Question

data class QuestionResponse(
        val id : Long?,
        val content : String,
        val answer : String?,
        val shareCount : Long,
        val shareStatus : Question.ShareStatus,
        val originId : Long?
) {
    companion object {
        fun of(question: Question): QuestionResponse {
            return QuestionResponse(
                    id = question.id,
                    content = question.content,
                    answer = question.answer,
                    shareCount = question.shareCount,
                    shareStatus = question.shareStatus,
                    originId = question.originId
            )
        }
    }
}
