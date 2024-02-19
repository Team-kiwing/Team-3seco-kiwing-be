package com.kw.data.domain.bundle.repository

import com.kw.data.domain.bundle.BundleTag
import org.springframework.data.jpa.repository.JpaRepository

interface BundleTagRepository : JpaRepository<BundleTag, Long> {

    fun deleteAllByBundleId(bundleId: Long)
}
