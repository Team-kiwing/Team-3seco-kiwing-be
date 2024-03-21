package com.kw.data.domain.question

import com.kw.data.domain.Base
import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.member.Member
import jakarta.persistence.*
import org.hibernate.annotations.NotFound
import org.hibernate.annotations.NotFoundAction

@Entity
class Question(
    content: String,
    answer: String? = null,
    answerShareType: AnswerShareType,
    isSearchable: Boolean,
    originId: Long? = null,
    bundle: Bundle,
    member: Member
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

    /**
     * 질문의 꾸러미가 조회될 때 마다
     * isSearchable이 true이면 exposeCount가 1 증가한다.
     * isSearchable이 false이면 원본의 exposeCount가 1 증가한다.
     */
    @Column(name = "expose_count", nullable = false)
    var exposeCount: Long = 0
        protected set

    @Column(name = "popularity", nullable = false)
    var popularity: Double = 0.0
        protected set

    /**
     * originId가 null인 경우 원본이므로 isSearchable=true 여야 한다.
     * 복제본은 isSearchable=false이되, 원본과 content가 달라질 경우, isSearchable=true로 변경되어야 한다.
     * 원본이 삭제될 경우, 복제본 중 isSearchable=false이면서 가장 먼저 생성된 복제본은 isSearchable=true로 변경되어야 한다.
     */
    @Column(name = "is_searchable", nullable = false, updatable = true)
    var isSearchable: Boolean = isSearchable
        protected set

    @Column(name = "origin_id", nullable = true, updatable = true)
    val originId: Long? = originId

    @OneToMany(mappedBy = "question", cascade = [CascadeType.ALL], orphanRemoval = true)
    var questionTags: MutableList<QuestionTag> = mutableListOf()
        protected set

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, updatable = false, foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val member: Member? = member

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_id", nullable = false, updatable = false)
    var bundle: Bundle = bundle
        protected set

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

    fun isWriter(memberId: Long?): Boolean {
        if (memberId == null) {
            return false
        }
        return member?.id == memberId
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

    fun updateSearchableStatus(isSearchable: Boolean) {
        this.isSearchable = isSearchable
    }

    fun updatePopularity(popularity: Double) {
        this.popularity = popularity
    }

    fun updateQuestionTags(questionTags: List<QuestionTag>) {
        if (questionTags.size > 3) {
            throw IllegalArgumentException("태그는 최대 3개까지 지정 가능합니다.")
        }
        this.questionTags.clear()
        this.questionTags.addAll(questionTags)
    }

    fun copy(bundle: Bundle, member: Member): Question {
        val question = Question(
            content = this.content,
            answer = if (this.answerShareType === AnswerShareType.PUBLIC) this.answer else null,
            answerShareType = AnswerShareType.PUBLIC,
            isSearchable = false,
            originId = this.id,
            bundle = bundle,
            member = member
        )
        question.updateQuestionTags(this.questionTags.map { QuestionTag(question, it.tag) })
        return question
    }
}
