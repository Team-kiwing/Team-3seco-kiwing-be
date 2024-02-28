package com.kw.api.domain.question.dto.response

import com.kw.api.domain.tag.dto.response.TagResponse
import com.kw.data.domain.question.Question
import java.time.LocalDateTime

data class QuestionResponse(
    val id: Long,
    val content: String,
    val answer: String?,
    val answerShareType: Question.AnswerShareType,
    val shareCount: Long,
    val originId: Long?,
    val tags: List<TagResponse>,
    val isHot: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(question: Question): QuestionResponse {
            return QuestionResponse(
                id = question.id!!,
                content = question.content,
                answer = question.answer,
                answerShareType = question.answerShareType,
                shareCount = question.shareCount,
                originId = question.originId,
                tags = question.questionTags.map { it.tag }.map { TagResponse.from(it) },
                isHot = question.isHot(),
                createdAt = question.createdAt,
                updatedAt = question.updatedAt
            )
        }
    }
}
