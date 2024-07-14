package com.kw.infrasecurity.refreshToken.repository

import com.kw.infrasecurity.refreshToken.entity.JpaRefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface JpaRefreshTokenRepository: JpaRepository<JpaRefreshToken, Long> {
    fun deleteByRefreshToken(refreshToken: String)

    fun findByRefreshToken(refreshToken: String): JpaRefreshToken
}
