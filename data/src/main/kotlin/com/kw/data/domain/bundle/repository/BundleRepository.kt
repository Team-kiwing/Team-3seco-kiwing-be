package com.kw.data.domain.bundle.repository

import com.kw.data.domain.bundle.Bundle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface BundleRepository : JpaRepository<Bundle, Long>, BundleCustomRepository {

    @Query("SELECT b FROM Bundle b WHERE b.member.id = :memberId AND b.id IN :ids")
    fun findAllByMemberIdAndIdIn(@Param("memberId") memberId: Long, @Param("ids") ids: List<Long>): List<Bundle>

    @Query(
        "SELECT b FROM Bundle b " +
                "LEFT JOIN FETCH b.bundleTags bt " +
                "LEFT JOIN FETCH bt.tag " +
                "LEFT JOIN fetch b.member " +
                "WHERE b.id = :id"
    )
    fun findWithTagsAndMemberById(@Param("id") id: Long): Bundle?

    @Modifying
    @Query("UPDATE Bundle b SET b.scrapeCount = b.scrapeCount + 1 WHERE b.id = :id")
    fun increaseScrapeCount(@Param("id") id: Long)

    @Modifying
    @Query("UPDATE Bundle b SET b.scrapeCount = b.scrapeCount - 1 WHERE b.id = :id")
    fun decreaseScrapeCount(@Param("id") id: Long)

    @Modifying
    @Query("UPDATE Bundle b SET b.viewCount = b.viewCount + 1 WHERE b.id = :id")
    fun increaseViewCount(@Param("id") id: Long)
}
