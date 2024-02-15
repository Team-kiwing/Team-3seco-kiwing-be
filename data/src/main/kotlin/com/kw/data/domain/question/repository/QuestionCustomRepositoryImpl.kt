package com.kw.data.domain.question.repository

import com.kw.data.domain.question.QQuestion.Companion.question
import com.kw.data.domain.question.Question
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class QuestionCustomRepositoryImpl(val jpaQueryFactory: JPAQueryFactory) : QuestionCustomRepository {
    override fun searchQuestion(keyword: String): List<Question> {
        return jpaQueryFactory.selectFrom(question)
            .where(
                containsKeyword(keyword)
            )
            .fetch()
    }

    private fun containsKeyword(keyword: String): BooleanExpression? {
        return question.content.contains(keyword)
    }
}
