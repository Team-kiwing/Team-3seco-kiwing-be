package com.kw.api.domain.question.dto.response

import com.kw.data.domain.question.QuestionReport

data class QuestionReportResponse(val id : Long?,
        val reason : String?,
        val questionId : Long?) {
    companion object {
        fun from(questionReport: QuestionReport) : QuestionReportResponse {
            return QuestionReportResponse(id = questionReport.id,
                    reason = questionReport.reason,
                    questionId = questionReport.question.id)
        }
    }
}
