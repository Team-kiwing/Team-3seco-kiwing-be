package com.kw.data.domain.bundle.repository

import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.bundle.QBundle.Companion.bundle
import com.kw.data.domain.bundle.dto.request.BundleGetCondition
import com.kw.data.domain.bundle.dto.request.BundleSearchCondition
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable

class BundleCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : BundleCustomRepository {

    override fun count(condition: BundleSearchCondition): Long {
        return queryFactory
            .select(bundle.count())
            .from(bundle)
            .where(condition.searchTerm?.let { bundle.name.contains(it) })
            .fetchOne()!!
    }

    override fun findAll(condition: BundleSearchCondition, pageable: Pageable): List<Bundle> {
        return queryFactory
            .selectFrom(bundle)
            .where(condition.searchTerm?.let { bundle.name.contains(it) })
            .orderBy(
                condition.sortingType?.let {
                    when (val sortingType = condition.sortingType) {
                        else -> when (BundleSearchCondition.SortingType.from(sortingType)) {
                            BundleSearchCondition.SortingType.RECOMMENDED -> TODO() //TODO
                            BundleSearchCondition.SortingType.LATEST -> bundle.createdAt.desc()
                            BundleSearchCondition.SortingType.POPULAR -> TODO() //TODO
                        }
                    }
                }
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
    }

    override fun findAllByMemberId(memberId: Long, condition: BundleGetCondition): List<Bundle> {
        return queryFactory
            .selectFrom(bundle)
            .where(bundle.member.id.eq(memberId))
            .orderBy(
                condition.sortingType?.let {
                    when (val sortingType = condition.sortingType) {
                        else -> when (BundleGetCondition.SortingType.from(sortingType)) {
                            BundleGetCondition.SortingType.LATEST -> bundle.createdAt.desc()
                            BundleGetCondition.SortingType.CREATED -> bundle.createdAt.asc()
                            BundleGetCondition.SortingType.UPDATED -> bundle.updatedAt.desc()
                        }
                    }
                }
            )
            .fetch()
    }

}
