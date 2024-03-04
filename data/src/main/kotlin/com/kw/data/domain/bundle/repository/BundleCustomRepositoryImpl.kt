package com.kw.data.domain.bundle.repository

import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.bundle.QBundle.Companion.bundle
import com.kw.data.domain.bundle.QBundleTag.Companion.bundleTag
import com.kw.data.domain.bundle.dto.request.BundleGetCondition
import com.kw.data.domain.bundle.dto.request.BundleSearchCondition
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable

class BundleCustomRepositoryImpl(private val queryFactory: JPAQueryFactory) : BundleCustomRepository {

    override fun count(condition: BundleSearchCondition): Long {
        val query = queryFactory
            .select(bundle.count())
            .from(bundle)
            .where(
                bundle.shareType.eq(Bundle.ShareType.PUBLIC),
                condition.keyword?.let { bundle.name.contains(it) }
            )

        if (condition.tagIds != null) {
            query
                .leftJoin(bundle.bundleTags, bundleTag).fetchJoin()
                .where(bundleTag.tag.id.`in`(condition.tagIds))
        }

        return query.fetchOne()!!
    }

    override fun findAll(condition: BundleSearchCondition, pageable: Pageable): List<Bundle> {
        val query = queryFactory
            .selectFrom(bundle)
            .leftJoin(bundle.bundleTags, bundleTag).fetchJoin()
            .where(
                bundle.shareType.eq(Bundle.ShareType.PUBLIC),
                condition.keyword?.let { bundle.name.contains(it) }
            )
            .orderBy(
                if (condition.sortingType == null) {
                    bundle.scrapeCount.desc()
                } else {
                    when (condition.sortingType) {
//                        BundleSearchCondition.SortingType.RECOMMENDED -> TODO() //TODO
                        BundleSearchCondition.SortingType.LATEST -> bundle.createdAt.desc()
                        BundleSearchCondition.SortingType.POPULAR -> bundle.scrapeCount.desc()
                    }
                }
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())

        if (condition.tagIds != null) {
            query.where(bundleTag.tag.id.`in`(condition.tagIds))
        }

        return query.fetch()
    }

    override fun findAllByMemberId(memberId: Long, condition: BundleGetCondition): List<Bundle> {
        return queryFactory
            .selectFrom(bundle)
            .leftJoin(bundle.bundleTags, bundleTag).fetchJoin()
            .where(bundle.member.id.eq(memberId))
            .orderBy(
                if (condition.sortingType == null) {
                    bundle.updatedAt.desc()
                } else {
                    when (condition.sortingType) {
                        BundleGetCondition.SortingType.LATEST -> bundle.createdAt.desc()
                        BundleGetCondition.SortingType.CREATED -> bundle.createdAt.asc()
                        BundleGetCondition.SortingType.UPDATED -> bundle.updatedAt.desc()
                    }
                }
            )
            .fetch()
    }

}
