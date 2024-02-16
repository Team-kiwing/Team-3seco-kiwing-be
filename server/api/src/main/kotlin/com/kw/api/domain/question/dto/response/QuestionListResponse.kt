package com.kw.api.domain.question.dto.response

import com.kw.api.common.dto.PageResponse

data class QuestionListResponse (val questionResponses : List<QuestionResponse>, val pageResponse: PageResponse)
