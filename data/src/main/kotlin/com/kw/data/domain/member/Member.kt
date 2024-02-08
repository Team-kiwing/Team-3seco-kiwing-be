package com.kw.data.domain.member

import com.kw.data.domain.Base
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Member(username: String, nickname: String, email: String) : Base() {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    val id: String? = null

    @Column(name = "name", nullable = false)
    val username: String = username

    @Column(name = "nickname", nullable = false)
    var nickname: String = nickname
        private set

    @Column(name = "email", nullable = false, updatable = false)
    val email: String = email

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, updatable = false)
    val provider: Provider = Provider.GOOGLE

    @Column(name = "last_logined_at", nullable = false)
    var lastLoginedAt: LocalDateTime = LocalDateTime.now()
        private set

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
        private set

    enum class Provider {
        GOOGLE,
    }
}
