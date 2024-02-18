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
                condition.sortingType?.let {
                    when (BundleSearchCondition.SortingType.from(condition.sortingType)) {
//                        BundleSearchCondition.SortingType.RECOMMENDED -> TODO() //TODO
                        BundleSearchCondition.SortingType.LATEST -> bundle.createdAt.desc()
                        BundleSearchCondition.SortingType.POPULAR -> TODO() //TODO
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
        return queryFactory
            .selectFrom(bundle)
            .leftJoin(bundle.bundleTags, bundleTag).fetchJoin()
            .leftJoin(bundleTag.tag, tag).fetchJoin()
            .leftJoin(bundle.questions, question).fetchJoin()
            .leftJoin(question).on(question.id.eq(originQuestion.id)).fetchJoin()
            .leftJoin(originQuestion.member).fetchJoin()
            .where(
                bundle.id.eq(id),
                (showOnlyMyQuestions == true && memberId != null).let {
                    originQuestion.member.id.eq(memberId)
                }
            )
            .fetchOne()
    }

}
