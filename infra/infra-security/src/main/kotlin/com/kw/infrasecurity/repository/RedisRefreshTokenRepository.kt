package com.kw.infrasecurity.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit


@Repository
class RedisRefreshTokenRepository(val redisTemplate: RedisTemplate<String, Any>): RefreshTokenRepository {
    override fun save(refreshToken: String, memberId: Long) {
        val valueOperations: ValueOperations<String, Any> = redisTemplate.opsForValue()
        valueOperations[refreshToken] = memberId
        redisTemplate.expire(refreshToken, REFRESH_TOKEN_EXPIRE_LONG, TimeUnit.SECONDS)
    }

    override fun delete(refreshToken: String) {
        redisTemplate.delete(refreshToken)
    }

    override fun findByRefreshToken(refreshToken: String): Long? {
        val memberId = redisTemplate.opsForValue().get(refreshToken) ?: return null
        return memberId.toString().toLong()
    }

    companion object {
        private const val REFRESH_TOKEN_EXPIRE_LONG = 259200L
    }
}

