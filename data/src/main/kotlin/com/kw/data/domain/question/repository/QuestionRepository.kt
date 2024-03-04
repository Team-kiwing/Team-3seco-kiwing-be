package com.kw.data.domain.question.repository

import com.kw.data.domain.question.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface QuestionRepository : JpaRepository<Question, Long>, QuestionCustomRepository {

    fun countAllByBundleId(bundleId: Long): Long

    fun findAllByBundleId(bundleId: Long): List<Question>

    @Query("SELECT q FROM Question q LEFT JOIN FETCH q.questionTags qt LEFT JOIN FETCH qt.tag WHERE q.id IN :ids")
    fun findAllWithTagsByIdIn(ids: List<Long>): List<Question>

    @Query("SELECT q FROM Question q LEFT JOIN FETCH q.questionTags qt LEFT JOIN FETCH qt.tag WHERE q.bundle.id = :bundleId")
    fun findAllWithTagsByBundleId(@Param("bundleId") bundleId: Long): List<Question>

    @Modifying
    @Query("UPDATE Question q SET q.shareCount = q.shareCount + 1 WHERE q.id IN :ids")
    fun increaseShareCountByIdIn(@Param("ids") ids: List<Long>)

    @Modifying
    @Query("UPDATE Question q SET q.shareCount = q.shareCount - 1 WHERE q.id IN :ids")
    fun decreaseShareCountByIdIn(@Param("ids") ids: List<Long>)
}
