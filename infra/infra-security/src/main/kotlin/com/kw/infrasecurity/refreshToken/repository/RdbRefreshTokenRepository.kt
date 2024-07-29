package com.kw.infrasecurity.refreshToken.repository

import com.kw.infrasecurity.refreshToken.entity.JpaRefreshToken
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import java.time.Duration
import java.time.LocalDateTime

@Primary
@Repository
class RdbRefreshTokenRepository(val jpaRefreshTokenRepository: JpaRefreshTokenRepository): RefreshTokenRepository {
    override fun save(refreshToken: String, memberId: Long) {
        val jpaRefreshToken = JpaRefreshToken(
                refreshToken = refreshToken,
                memberId = memberId)
        jpaRefreshTokenRepository.save(jpaRefreshToken)
    }

    override fun delete(refreshToken: String) {
        jpaRefreshTokenRepository.deleteByRefreshToken(refreshToken)
    }

    override fun findByRefreshToken(refreshToken: String): Long? {
        val jpaRefreshToken = jpaRefreshTokenRepository.findByRefreshToken(refreshToken) ?: return null

        if(isRefreshTokenExpired(jpaRefreshToken))
            return null

        return jpaRefreshToken.memberId
    }
    // TODO 테스트 해보기

    private fun isRefreshTokenExpired(refreshToken: JpaRefreshToken): Boolean {
        val timeDifference = Duration.between(LocalDateTime.now(), refreshToken.createdAt)
        if(timeDifference.toSeconds() < REFRESH_TOKEN_EXPIRE_LONG) {
            return false
        }
        return true
    }

    // TODO 만료된 refresh token 처리 구현하기
    companion object {
        private const val REFRESH_TOKEN_EXPIRE_LONG = 259200L
    }
    // TODO 만료기간 yml에서 가져오게 수정하기
}
