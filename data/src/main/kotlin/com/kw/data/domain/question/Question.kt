package com.kw.data.domain.question

import com.kw.data.domain.Base
import com.kw.data.domain.member.Member
import jakarta.persistence.*

@Entity
class Question(content: String, originId: Long?, shareStatus: ShareStatus = ShareStatus.AVAILABLE) : Base() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "content", nullable = false, updatable = true)
    var content: String = content
        protected set

    @Column(name = "answer", nullable = true, updatable = true)
    var answer: String? = null
        protected set

    @Column(name = "share_count", nullable = false, updatable = true)
    var shareCount: Long = 0
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "share_status", nullable = false, updatable = true)
    var shareStatus: ShareStatus = shareStatus
        protected set

    @Column(name = "origin_id", nullable = true, updatable = true)
    var originId: Long? = originId
        protected set

    @OneToMany(mappedBy = "question", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
    var questionTags : List<QuestionTag>? = mutableListOf();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, updatable = false)
    val member: Member? = null //TODO: Member? -> Member 타입 수정

    enum class ShareStatus {
        AVAILABLE, NON_AVAILABLE;

        companion object {
            fun of(input: String): ShareStatus {
                try {
                    return valueOf(input)
                } catch (e: Exception) {
                    throw IllegalArgumentException("존재하지 않는 공유 상태입니다")
                }
            }
        }
    }

    fun updateQuestionAnswer(answer: String) {
        this.answer = answer
    }

    fun updateQuestionContent(content: String) {
        this.content = content
    }

    fun updateQuestionStatus(shareStatus: ShareStatus) {
        this.shareStatus = shareStatus
    }

    fun increaseShareCount() {
        this.shareCount++;
    }

    fun updateQuestionQuestionTags(questionTags: List<QuestionTag>?) {
        this.questionTags = questionTags
    }

    fun copy(): Question {
        increaseShareCount() //TODO: 동시성 고려
        return Question(
            content = this.content,
            shareStatus = ShareStatus.AVAILABLE,
            originId = this.originId ?: this.id,
        )
    }
}
