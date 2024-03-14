package com.kw.data.domain.question.repository

import com.kw.data.domain.question.Question
import com.kw.data.domain.question.dto.QuestionSearchDto

interface QuestionCustomRepository {
    fun searchQuestion(questionSearchDto: QuestionSearchDto): List<Question>
    fun getPageNum(questionSearchDto: QuestionSearchDto): Long

    fun findAllWithTagsByBundleId(bundleId: Long, showOnlyMyQuestions: Boolean?, memberId: Long?): List<Question>

    fun findNotSearchableFirstOneByOriginId(originId: Long): Question?
}
