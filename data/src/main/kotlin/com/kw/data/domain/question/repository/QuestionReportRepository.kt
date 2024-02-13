package com.kw.data.domain.question.repository

import com.kw.data.domain.question.QuestionReport
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionReportRepository : JpaRepository<QuestionReport, Long> {
}
