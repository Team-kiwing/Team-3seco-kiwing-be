package com.kw.data.domain.member

import com.kw.data.domain.Base
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Member(
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    val id: String,

    @Column(name = "nickname", nullable = false)
    var nickname: String,

    @Column(name = "email", nullable = false, updatable = false)
    val email: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, updatable = false)
    val provider: ProviderType,

    @Column(name = "last_logined_at", nullable = false)
    var lastLoginedAt: LocalDateTime,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null,
) : Base()
