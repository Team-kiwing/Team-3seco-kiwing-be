package com.kw.data.domain.bundle

import com.kw.data.domain.Base
import com.kw.data.domain.member.Member
import com.kw.data.domain.question.Question
import jakarta.persistence.*
import org.hibernate.annotations.NotFound
import org.hibernate.annotations.NotFoundAction

@Entity
class Bundle(
    name: String,
    shareType: ShareType,
    originId: Long? = null,
    member: Member,
) : Base() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Long? = null

    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "share_type", nullable = false)
    var shareType: ShareType = shareType
        protected set

    @Column(name = "question_order", nullable = false)
    var questionOrder: String = ""
        protected set

    @Column(name = "scrape_count", nullable = false)
    var scrapeCount: Long = 0
        protected set

    /**
     * 작성자의 조회수는 포함되지 않는다.
     */
    @Column(name = "view_count", nullable = false)
    var viewCount: Long = 0
        protected set

    @Column(name = "popularity", nullable = false)
    var popularity: Double = 0.0
        protected set

    @Column(name = "origin_id", nullable = true, updatable = true)
    val originId: Long? = originId

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, updatable = false)
    val member: Member? = member

    @OneToMany(mappedBy = "bundle", cascade = [CascadeType.ALL], orphanRemoval = true)
    var bundleTags: MutableList<BundleTag> = mutableListOf()
        protected set

    @OneToMany(mappedBy = "bundle", cascade = [CascadeType.ALL], orphanRemoval = true)
    var questions: MutableList<Question> = mutableListOf()
        protected set

    enum class ShareType {
        PUBLIC,
        PRIVATE;

        companion object {
            fun from(input: String): ShareType {
                try {
                    return ShareType.valueOf(input.uppercase())
                } catch (e: Exception) {
                    throw IllegalArgumentException("존재하지 않는 공개 범위 타입입니다: $input")
                }
            }
        }
    }

    fun isHot(): Boolean {
        return scrapeCount >= 30
    }

    fun isWriter(memberId: Long?): Boolean {
        if (memberId == null) {
            return false
        }
        return member?.id == memberId
    }

    fun updateName(name: String) {
        this.name = name
    }

    fun updateShareType(shareType: ShareType) {
        this.shareType = shareType
    }

    fun updateQuestionOrder(questionOrder: String) {
        this.questionOrder = questionOrder
    }

    fun updateBundleTags(bundleTags: List<BundleTag>) {
        if (bundleTags.size > 3) {
            throw IllegalArgumentException("태그는 최대 3개까지 지정 가능합니다.")
        }
        this.bundleTags.clear()
        this.bundleTags.addAll(bundleTags)
    }

    fun addQuestions(questions: List<Question>) {
        this.questions.addAll(questions)
        updateQuestionOrder((this.questionOrder + " " + questions.joinToString(" ") { it.id.toString() }).trim())
    }

    fun removeQuestions(questions: List<Question>) {
        this.questions.removeAll(questions)
        val questionOrderList = this.questionOrder.split(" ").toMutableList()
        questions.forEach { questionOrderList.remove(it.id.toString()) }
        updateQuestionOrder(questionOrderList.joinToString(" "))
    }

    fun copy(member: Member): Bundle {
        val bundle = Bundle(
            name = this.name,
            shareType = ShareType.PRIVATE,
            originId = this.id,
            member = member
        )
        bundle.updateBundleTags(this.bundleTags.map { BundleTag(bundle, it.tag) })
        return bundle
    }
}


