package com.kw.data.domain.bundle.repository

import com.kw.data.domain.bundle.Bundle
import com.kw.data.domain.bundle.QBundle.Companion.bundle
import com.kw.data.domain.bundle.QBundleTag.Companion.bundleTag
import com.kw.data.domain.bundle.dto.request.BundleGetCondition
import com.kw.data.domain.bundle.dto.request.BundleSearchCondition
import com.kw.data.domain.question.QQuestion
import com.kw.data.domain.question.QQuestion.Companion.question
import com.kw.data.domain.tag.QTag.Companion.tag
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
                if (condition.sortingType == null) {
                    bundle.scrapeCount.desc()
                } else {
                    when (BundleSearchCondition.SortingType.from(condition.sortingType)) {
//                        BundleSearchCondition.SortingType.RECOMMENDED -> TODO() //TODO
                        BundleSearchCondition.SortingType.LATEST -> bundle.createdAt.desc()
                        BundleSearchCondition.SortingType.POPULAR -> bundle.scrapeCount.desc()
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
                if (condition.sortingType == null) {
                    bundle.createdAt.desc()
                } else {
                    when (BundleGetCondition.SortingType.from(condition.sortingType)) {
                        BundleGetCondition.SortingType.LATEST -> bundle.createdAt.desc()
                        BundleGetCondition.SortingType.CREATED -> bundle.createdAt.asc()
                        BundleGetCondition.SortingType.UPDATED -> bundle.updatedAt.desc()
                    }
                }
            )
            .fetch()
    }

    override fun findDetailById(id: Long, showOnlyMyQuestions: Boolean?, memberId: Long?): Bundle? {
        val originQuestion = QQuestion("originQuestion")

        val query = queryFactory
            .selectFrom(bundle)
            .leftJoin(bundle.bundleTags, bundleTag)
            .leftJoin(bundleTag.tag, tag)
            .leftJoin(bundle.questions, question)
            .where(bundle.id.eq(id))

        if (showOnlyMyQuestions == true && memberId != null) {
            query
                .leftJoin(originQuestion).on(originQuestion.id.eq(question.originId))
                .leftJoin(originQuestion.member)
                .where(originQuestion.member.id.eq(memberId))
        }

        return query.fetchOne()
    }

}
