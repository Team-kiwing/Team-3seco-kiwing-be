package com.kw.infrasecurity.refreshToken.entity

import jakarta.persistence.*

@Entity
@Table(name = "refresh_token")
class JpaRefreshToken(@Column(name = "refresh_token", nullable = false, updatable = false)
                      val refreshToken: String,
                      @Column(name = "member_id", nullable = false, updatable = false)
                      val memberId: Long) {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

