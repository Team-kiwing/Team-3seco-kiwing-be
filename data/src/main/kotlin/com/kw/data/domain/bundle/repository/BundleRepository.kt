package com.kw.data.domain.bundle.repository

import com.kw.data.domain.bundle.Bundle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BundleRepository : JpaRepository<Bundle, Long> {

    @Query(
        "SELECT b " +
                "FROM Bundle b " +
                "JOIN FETCH b.bundleTags bt " +
                "JOIN FETCH bt.tag " +
                "JOIN FETCH b.questions q " +
                "WHERE b.id = :id"
    )
    fun findDetailById(id: Long): Bundle?

}
