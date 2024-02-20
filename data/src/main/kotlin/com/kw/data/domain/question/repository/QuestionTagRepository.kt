package com.kw.data.domain.question.repository

import com.kw.data.domain.question.QuestionTag
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionTagRepository : JpaRepository<QuestionTag, Long> {

    fun deleteAllByQuestionId(questionId: Long)

}
