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

    @Column(name = "scrape_count", nullable = false)
    var scrapeCount: Long = 0
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member? = null //TODO: Member? -> Member 타입 수정, nullable = false 추가

    @OneToMany(mappedBy = "bundle", cascade = [CascadeType.ALL])
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

    fun updateName(name: String) {
        this.name = name
    }

    fun updateShareType(shareType: ShareType) {
        this.shareType = shareType
    }

    fun updateBundleTags(bundleTags: List<BundleTag>) {
        this.bundleTags = bundleTags.toMutableList()
    }

    fun addQuestions(questions: List<Question>) {
        this.questions.addAll(questions)
    }

    fun removeQuestions(questions: List<Question>) {
        this.questions.removeAll(questions)
    }

    fun increaseScrapeCount() {
        this.scrapeCount++
    }

    fun copy(): Bundle {
        increaseScrapeCount() //TODO: 동시성 고려
        val bundle = Bundle(this.name, this.shareType)
        bundle.updateBundleTags(this.bundleTags.map { BundleTag(bundle, it.tag) })
        bundle.addQuestions(this.questions.map(Question::copy))
        return bundle
    }

}


