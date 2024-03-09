package com.kw.data.domain.question.dto

import com.kw.data.common.dto.SearchSortingType

data class QuestionSearchDto(
    val sortingType: SearchSortingType?,
    val tagIds: List<Long>?,
    val keyword: String?,
    val page: Long = 1,
    val size: Long = 10
)
