package com.kw.data.domain.question

import com.kw.data.domain.Base
import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.member.Member
import jakarta.persistence.*

@Entity
class Question(
    content: String,
    answer: String? = null,
    answerShareType: AnswerShareType,
    originId: Long? = null,
    bundle: Bundle,
) : Base() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "content", nullable = false, updatable = true)
    var content: String = content
        protected set

    @Column(name = "answer", nullable = true, updatable = true)
    var answer: String? = answer
        protected set

    @Column(name = "share_count", nullable = false, updatable = true)
    var shareCount: Long = 0
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "answer_share_type", nullable = false, updatable = true)
    var answerShareType: AnswerShareType = answerShareType
        protected set

    @Column(name = "origin_id", nullable = true, updatable = true)
    var originId: Long? = originId
        protected set

    @OneToMany(mappedBy = "question", cascade = [CascadeType.ALL], orphanRemoval = true)
    var questionTags: MutableList<QuestionTag> = mutableListOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", updatable = false)
    val member: Member? = null //TODO: Member? -> Member 타입 수정, nullable = false 추가

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_id", nullable = false, updatable = false)
    var bundle: Bundle = bundle

    enum class AnswerShareType {
        PUBLIC, PRIVATE;

        companion object {
            fun from(input: String): AnswerShareType {
                try {
                    return valueOf(input.uppercase())
                } catch (e: Exception) {
                    throw IllegalArgumentException("존재하지 않는 답변 공유 상태입니다")
                }
            }
        }
    }

    fun isHot(): Boolean {
        return shareCount >= 30
    }

    fun updateAnswer(answer: String?) {
        this.answer = answer
    }

    fun updateContent(content: String) {
        this.content = content
    }

    fun updateAnswerShareStatus(answerShareType: AnswerShareType) {
        this.answerShareType = answerShareType
    }

    fun updateQuestionTags(questionTags: List<QuestionTag>) {
        if (questionTags.size >= 3) {
            throw IllegalArgumentException("태그는 최대 3개까지 지정 가능합니다.")
        }
        this.questionTags.clear()
        this.questionTags.addAll(questionTags)
    }

    fun increaseShareCount() {
        this.shareCount++;
    }

    fun copy(bundle: Bundle): Question {
        increaseShareCount() //TODO: 동시성 고려
        val question = Question(
            content = this.content,
            answer = if (this.answerShareType === AnswerShareType.PUBLIC) this.answer else null,
            answerShareType = AnswerShareType.PUBLIC,
            originId = this.originId ?: this.id,
            bundle = bundle
        )
        question.updateQuestionTags(this.questionTags.map { QuestionTag(question, it.tag) })
        return question
    }
}
