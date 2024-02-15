package com.kw.data.domain.bundle.repository

import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.bundle.dto.request.BundleGetCondition

interface BundleQuerydslRepository {
    fun findAllByMemberId(memberId: Long, condition: BundleGetCondition): List<Bundle>
}
