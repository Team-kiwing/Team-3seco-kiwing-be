package com.kw.infraquerydsl.domain.question

import com.kw.data.domain.question.QQuestion.Companion.question
import com.kw.data.domain.question.Question
import com.kw.infraquerydsl.domain.question.dto.QuestionSearchDto
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class QuestionCustomRepositoryImpl(val jpaQueryFactory: JPAQueryFactory ) : QuestionCustomRepository {
    override fun searchQuestion(questionSearchDto: QuestionSearchDto) : List<Question> {
        val keyword = questionSearchDto.keyword
        val page = questionSearchDto.page
        val size = questionSearchDto.size

        return jpaQueryFactory.selectFrom(question)
            .where(
                containsKeyword(keyword)
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
                containsKeyword(keyword)
            )
            .fetchOne()
        if (count == 0L) {
            return 1L
        }
        return if (count!! % size != 0L) {
            count / size + 1
        } else count / size
    }
}
