package com.kw.infraquerydsl.domain.question

import com.kw.data.domain.question.QQuestion.Companion.question
import com.kw.data.domain.question.Question
import com.kw.infraquerydsl.domain.question.dto.QuestionSearchDto
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class QuestionCustomRepositoryImpl(val jpaQueryFactory: JPAQueryFactory ) : QuestionCustomRepository {
    val PAGE_OFFSET : Long = 10L

    override fun searchQuestion(questionSearchDto: QuestionSearchDto) : List<Question> {
        val keyword = questionSearchDto.keyword
        val page = questionSearchDto.page

        return jpaQueryFactory.selectFrom(question)
            .where(
                containsKeyword(keyword)
            )
            .offset((page - 1) * PAGE_OFFSET)
            .limit(PAGE_OFFSET)
            .fetch()
    }

    private fun containsKeyword(keyword: String): BooleanExpression? {
        return question.content.contains(keyword)
    }

    fun getPageNum(questionSearchDto: QuestionSearchDto): Long? {
        val keyword = questionSearchDto.keyword
        val page = questionSearchDto.page

        val count = jpaQueryFactory.select(question.count())
            .from(question)
            .where(
                containsKeyword(keyword)
            )
            .fetchOne()
        if (count == 0L) {
            return 1L
        }
        return if (count!! % PAGE_OFFSET != 0L) {
            count / PAGE_OFFSET + 1
        } else count / PAGE_OFFSET
    }
}
