package com.kw.infrasecurity.refreshToken.repository

interface RefreshTokenRepository {
    fun save(refreshToken: String, memberId: Long)

    fun delete(refreshToken: String)

    fun findByRefreshToken(refreshToken: String): Long?
}
