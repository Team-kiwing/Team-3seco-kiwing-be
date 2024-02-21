package com.kw.data.domain.question.repository

import com.kw.data.domain.question.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface QuestionRepository : JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q LEFT JOIN FETCH q.questionTags qt LEFT JOIN FETCH qt.tag WHERE q.bundle.id = :bundleId")
    fun findAllWithTagsByBundleId(@Param("bundleId") bundleId: Long): List<Question>

}
