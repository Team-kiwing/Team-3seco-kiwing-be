package com.kw.data.domain.question

import com.kw.data.domain.Base
import jakarta.persistence.*

@Entity
class Question(content : String, originId : Long?, shareStatus: ShareStatus) : Base() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

    @Column(name = "content", nullable = false, updatable = true)
    var content : String = content
        protected set

    @Column(name = "answer", nullable = true, updatable = true)
    var answer : String? = null
        protected set

    @Column(name = "share_count", nullable = false, updatable = true)
    var shareCount : Long = 0
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "share_status", nullable = false, updatable = true)
    var shareStatus : ShareStatus = ShareStatus.AVAILABLE
        protected set

    @Column(name = "origin_id", nullable = true, updatable = true)
    var originId : Long? = originId
        protected set

    @OneToMany(mappedBy = "question", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
    var questionTags : List<QuestionTag>? = mutableListOf();

    enum class ShareStatus {
        AVAILABLE, NON_AVAILABLE;

        companion object {
            fun of(input : String) : ShareStatus {
                try {
                    return valueOf(input)
                } catch (e : Exception) {
                    throw IllegalArgumentException("존재하지 않는 공유 상태입니다")
                }
            }
        }
    }

    fun updateQuestionAnswer(answer : String) {
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
}
