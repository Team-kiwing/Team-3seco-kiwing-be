package com.kw.data.domain.bundle.repository

import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.bundle.dto.request.BundleGetCondition
import com.kw.data.domain.bundle.dto.request.BundleSearchCondition
import org.springframework.data.domain.Pageable

interface BundleCustomRepository {

    fun count(condition: BundleSearchCondition): Long

    fun findAll(condition: BundleSearchCondition, pageable: Pageable): List<Bundle>

    fun findAllByMemberId(memberId: Long, condition: BundleGetCondition): List<Bundle>

    fun findDetailById(id: Long, showOnlyMyQuestions: Boolean?, memberId: Long?): Bundle?
}
