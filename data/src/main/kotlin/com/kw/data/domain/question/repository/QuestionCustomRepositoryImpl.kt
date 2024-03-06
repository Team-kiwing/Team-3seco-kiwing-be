package com.kw.data.domain.question.repository

import com.kw.data.domain.question.QQuestion.Companion.question
import com.kw.data.domain.question.QQuestionTag.Companion.questionTag
import com.kw.data.domain.question.Question
import com.kw.infraquerydsl.domain.question.dto.QuestionSearchDto
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class QuestionCustomRepositoryImpl(val jpaQueryFactory: JPAQueryFactory) : QuestionCustomRepository {
    override fun searchQuestion(questionSearchDto: QuestionSearchDto): List<Question> {
        val keyword = questionSearchDto.keyword
        val page = questionSearchDto.page
        val size = questionSearchDto.size

        return jpaQueryFactory.selectFrom(question)
            .where(
                keyword?.let { containsKeyword(keyword) }
            )
            .offset((page - 1) * size)
            .limit(size)
            .fetch()
    }

    private fun containsKeyword(keyword: String): BooleanExpression? {
        return question.content.contains(keyword)
    }

    override fun getPageNum(questionSearchDto: QuestionSearchDto): Long {
        val keyword = questionSearchDto.keyword
        val page = questionSearchDto.page
        val size = questionSearchDto.size

        val count = jpaQueryFactory.select(question.count())
            .from(question)
            .where(
                keyword?.let { containsKeyword(keyword) }
            )
            .fetchOne()
        if (count == 0L) {
            return 1L
        }
        return if (count!! % size != 0L) {
            count / size + 1
        } else count / size
    }

    override fun findAllWithTagsByBundleId(
        bundleId: Long,
        showOnlyMyQuestions: Boolean?,
        memberId: Long?
    ): List<Question> {
        var query = jpaQueryFactory
            .selectFrom(question)
            .leftJoin(question.questionTags, questionTag).fetchJoin()
            .leftJoin(questionTag.tag).fetchJoin()
            .where(question.bundle.id.eq(bundleId))

        if (showOnlyMyQuestions == true && memberId != null) {
            query = query
                .leftJoin(question.member).fetchJoin()
                .where(question.member.id.eq(memberId))
        }

        return query.fetch()
    }

}
