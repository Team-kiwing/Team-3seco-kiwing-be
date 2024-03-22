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
    val writerId: Long?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {

    companion object {
        fun from(question: Question, requestMemberId: Long?, hotThreshold: Double): QuestionResponse {
            return QuestionResponse(
                id = question.id!!,
                content = question.content,
                answer = if (isAnswerVisible(question, requestMemberId)) question.answer else null,
                answerShareType = question.answerShareType,
                shareCount = question.shareCount,
                originId = question.originId,
                tags = question.questionTags.map { it.tag }.map { TagResponse.from(it) },
                isHot = question.isHot(hotThreshold),
                writerId = question.member?.id,
                createdAt = question.createdAt,
                updatedAt = question.updatedAt
            )
        }

        private fun isAnswerVisible(question: Question, requestMemberId: Long?): Boolean {
            if (requestMemberId == null) {
                return false
            }
            return !(question.answerShareType == Question.AnswerShareType.PRIVATE && !question.isWriter(requestMemberId))
        }
    }
}
