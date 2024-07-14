package com.kw.infrasecurity.repository

interface RefreshTokenRepository {
    fun save(refreshToken: String, memberId: Long)

    fun delete(refreshToken: String)

    fun findByRefreshToken(refreshToken: String): Long?
}
