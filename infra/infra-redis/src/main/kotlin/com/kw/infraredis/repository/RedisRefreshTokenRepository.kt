package com.kw.infraredis.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit


@Repository
class RedisRefreshTokenRepository(val redisTemplate: RedisTemplate<String, Any>) {
    fun save(refreshToken: String, memberId: Long) {
        val valueOperations: ValueOperations<String, Any> = redisTemplate.opsForValue()
        valueOperations[refreshToken] = memberId
        redisTemplate.expire(refreshToken, REFRESH_TOKEN_EXPIRE_LONG, TimeUnit.SECONDS)
    }

    fun delete(refreshToken: String) {
        redisTemplate.delete(refreshToken)
    }

    fun findByRefreshToken(refreshToken: String): Long? {
        return redisTemplate.opsForValue().get(refreshToken).toString().toLong()
    }

    companion object {
        private const val REFRESH_TOKEN_EXPIRE_LONG = 259200L
    }
}

