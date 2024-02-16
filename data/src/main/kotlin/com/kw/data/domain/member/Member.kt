package com.kw.data.domain.member

import com.kw.data.domain.Base
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Member(username: String, nickname: String, email: String) : Base() {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    val id: Long? = null

    @Column(name = "username", nullable = false)
    val username: String = username

    @Column(name = "nickname", nullable = false)
    var nickname: String = nickname
        protected set

    @Column(name = "email", nullable = false, updatable = false)
    val email: String = email

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, updatable = false)
    val provider: Provider = Provider.GOOGLE

    @Column(name = "last_logined_at", nullable = false)
    var lastLoginedAt: LocalDateTime = LocalDateTime.now()
        protected set

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
        protected set

    enum class Provider {
        GOOGLE,
    }
}
