package com.kw.data.domain.bundle

import com.kw.data.domain.Base
import com.kw.data.domain.member.Member
import com.kw.data.domain.question.Question
import jakarta.persistence.*

@Entity
class Bundle(
    name: String,
    shareType: ShareType
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

    @Column(name = "share_count", nullable = false)
    var shareCount: Long = 0
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member? = null //TODO: Member? -> Member 타입 수정, nullable = false 추가

    @OneToMany(mappedBy = "bundle", cascade = [CascadeType.ALL], orphanRemoval = true)
    var bundleTags: MutableList<BundleTag> = mutableListOf()

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    var questions: MutableList<Question> = mutableListOf()

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

    fun updateNameAndShareType(name: String, shareType: ShareType) {
        this.name = name
        this.shareType = shareType
    }

    fun addBundleTags(bundleTags: List<BundleTag>) {
        this.bundleTags.addAll(bundleTags)
    }

    fun addQuestions(questions: List<Question>) {
        this.questions.addAll(questions)
    }

    fun removeQuestions(questions: List<Question>) {
        this.questions.removeAll(questions)
    }

    fun increaseShareCount() {
        this.shareCount++
    }

    fun copy(): Bundle {
        increaseShareCount() //TODO: 동시성 고려
        val bundle = Bundle(this.name, this.shareType)
        bundle.addBundleTags(this.bundleTags.map { BundleTag(bundle, it.tag) })
        bundle.addQuestions(
            this.questions
                .filter { it.shareStatus === Question.ShareStatus.AVAILABLE }
                .map(Question::copy)
        )
        return bundle
    }

}


