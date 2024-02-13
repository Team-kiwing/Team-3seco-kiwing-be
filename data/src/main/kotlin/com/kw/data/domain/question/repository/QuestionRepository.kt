package com.kw.data.domain.question.repository

import com.kw.data.domain.question.Question
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepository : JpaRepository<Question, Long> {
}
