package com.kw.data.domain.question

import com.kw.data.domain.Base
import jakarta.persistence.*

@Entity
class Question(content : String, answer : String?, originId : Long?, shareStatus: ShareStatus) : Base() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

    @Column(name = "content", nullable = false, updatable = true)
    var content : String = content

    @Column(name = "answer", nullable = true, updatable = true)
    var answer : String? = answer

    @Column(name = "shareCount", nullable = false, updatable = true)
    var shareCount : Long = 0
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "share_status", nullable = false, updatable = true)
    var shareStatus : ShareStatus = ShareStatus.AVAILABLE
        protected set

    @Column(name = "origin_id", nullable = true, updatable = true)
    var originId : Long? = originId

    enum class ShareStatus {
        AVAILABLE, NON_AVAILABLE
    }
}
