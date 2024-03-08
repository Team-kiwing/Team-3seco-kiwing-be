package com.kw.api.domain.question.dto.request

import com.kw.data.common.dto.SearchSortingType

data class QuestionSearchRequest(
    val sortingType: SearchSortingType?,
    val tagIds: List<Long>?,
    val keyword: String?,
    val page: Long = 1,
    val size: Long = 10
)
