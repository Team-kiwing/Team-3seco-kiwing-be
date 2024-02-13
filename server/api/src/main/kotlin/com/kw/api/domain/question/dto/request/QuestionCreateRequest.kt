package com.kw.api.domain.question.dto.request

import com.kw.data.domain.question.Question

data class QuestionCreateRequest(
        val content : String,
        val shareStatus : Question.ShareStatus,
        val originId : Long?
) {
    fun toEntity() : Question {
        return Question(content = content,
                shareStatus = shareStatus,
                originId = originId
                )
    }
}
