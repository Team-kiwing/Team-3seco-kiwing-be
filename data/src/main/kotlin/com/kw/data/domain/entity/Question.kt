package com.kw.data.domain.entity

import jakarta.persistence.*

@Entity
class Question(content : String, answer : String, originId : Long) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null
    var content : String = content
    var answer : String? = answer
    var shareCount : Long = 0
        protected set
    @Enumerated(EnumType.STRING)
    var shareStatus : ShareStatus = ShareStatus.AVAILABLE
        protected set
    var originId : Long? = originId

    enum class ShareStatus {
        AVAILABLE, NON_AVAILABLE
    }
}
