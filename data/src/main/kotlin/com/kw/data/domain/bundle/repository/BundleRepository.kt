package com.kw.data.domain.bundle.repository

import com.kw.data.domain.bundle.Bundle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface BundleRepository : JpaRepository<Bundle, Long>, BundleCustomRepository {

    @Query("SELECT b FROM Bundle b LEFT JOIN FETCH b.bundleTags bt LEFT JOIN FETCH bt.tag WHERE b.id = :id")
    fun findWithTagsById(@Param("id") id: Long): Bundle?

}
