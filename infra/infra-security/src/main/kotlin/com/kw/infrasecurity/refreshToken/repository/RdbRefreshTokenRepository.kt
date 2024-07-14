package com.kw.infrasecurity.refreshToken.repository

import com.kw.infrasecurity.refreshToken.entity.JpaRefreshToken
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

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
        val jpaRefreshToken = jpaRefreshTokenRepository.findByRefreshToken(refreshToken) ?: throw RuntimeException()
        return jpaRefreshToken.memberId
    }

    // TODO 만료된 refresh token 처리 구현하기
}
