package com.kw.data.domain.bundle.repository

import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.bundle.QBundle.Companion.bundle
import com.kw.data.domain.bundle.dto.request.BundleGetCondition
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.jpa.impl.JPAQueryFactory

class BundleQuerydslRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : BundleQuerydslRepository {

    override fun findAllByMemberId(memberId: Long, condition: BundleGetCondition): List<Bundle> {
        return queryFactory
            .selectFrom(bundle)
            .where(bundle.member.id.eq(memberId))
            .orderBy(createSortingCondition(condition.sortingType))
            .fetch()
    }

    private fun createSortingCondition(sortingType: String?): OrderSpecifier<*> {
        if (sortingType == null) {
            return bundle.createdAt.desc()
        }
        return when (BundleGetCondition.SortingType.from(sortingType)) {
            BundleGetCondition.SortingType.LATEST -> bundle.createdAt.desc()
            BundleGetCondition.SortingType.CREATED -> bundle.createdAt.asc()
            BundleGetCondition.SortingType.UPDATED -> bundle.updatedAt.desc()
        }
    }

}
