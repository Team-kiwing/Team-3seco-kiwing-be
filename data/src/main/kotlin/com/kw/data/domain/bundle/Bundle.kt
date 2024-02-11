package com.kw.data.domain.bundle

import com.kw.data.domain.Base
import jakarta.persistence.*

@Entity
class Bundle(name: String) : Base() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Int? = null

    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "share_status", nullable = false)
    var shareStatus: ShareStatus = ShareStatus.PRIVATE
        protected set

    @Column(name = "share_count", nullable = false)
    var shareCount: Int? = 0
        protected set

    enum class ShareStatus {
        PUBLIC,
        PRIVATE,
    }
}


