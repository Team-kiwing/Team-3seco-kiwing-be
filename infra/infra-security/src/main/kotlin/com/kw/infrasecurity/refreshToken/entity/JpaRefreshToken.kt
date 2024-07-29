package com.kw.infrasecurity.refreshToken.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "refresh_token")
@EntityListeners(AuditingEntityListener::class)
class JpaRefreshToken(@Column(name = "refresh_token", nullable = false, updatable = false)
                      val refreshToken: String,
                      @Column(name = "member_id", nullable = false, updatable = false)
                      val memberId: Long) {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()
}

