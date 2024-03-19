package com.kw.data.domain.bundle.repository

import com.kw.data.common.dto.SearchSortingType
import com.kw.data.common.util.CustomFunction
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
                condition.keyword?.let { CustomFunction.match(bundle.name, it) }
            )

        if (condition.tagIds != null) {
            query
                .leftJoin(bundle.bundleTags, bundleTag)
                .where(bundleTag.tag.id.`in`(condition.tagIds))
        }

        return query.fetchOne()!!
    }

    override fun search(condition: BundleSearchCondition, pageable: Pageable): List<Bundle> {
        val query = queryFactory
            .selectFrom(bundle)
            .leftJoin(bundle.bundleTags, bundleTag).fetchJoin()
            .leftJoin(bundleTag.tag).fetchJoin()
            .leftJoin(bundle.member).fetchJoin()
            .where(
                bundle.shareType.eq(Bundle.ShareType.PUBLIC),
                condition.keyword?.let { CustomFunction.match(bundle.name, it) }
            )
            .orderBy(
                if (condition.sortingType == null) {
                    bundle.scrapeCount.desc()
                } else {
                    when (condition.sortingType) {
//                        SortingType.RECOMMENDED -> TODO() //TODO
                        SearchSortingType.LATEST -> bundle.createdAt.desc()
                        SearchSortingType.POPULAR -> bundle.scrapeCount.desc()
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

    override fun findAllWithMemberByMemberId(memberId: Long, condition: BundleGetCondition): List<Bundle> {
        return queryFactory
            .selectFrom(bundle)
            .leftJoin(bundle.bundleTags, bundleTag).fetchJoin()
            .leftJoin(bundle.member).fetchJoin()
            .where(bundle.member.id.eq(memberId))
            .orderBy(
                if (condition.sortingType == null) {
                    bundle.updatedAt.desc()
                } else {
                    when (condition.sortingType) {
                        BundleGetCondition.SortingType.CUSTOM -> bundle.id.asc() // 서비스에서 재정렬
                        BundleGetCondition.SortingType.LATEST -> bundle.createdAt.desc()
                        BundleGetCondition.SortingType.CREATED -> bundle.createdAt.asc()
                        BundleGetCondition.SortingType.UPDATED -> bundle.updatedAt.desc()
                    }
                }
            )
            .fetch()
    }
}
