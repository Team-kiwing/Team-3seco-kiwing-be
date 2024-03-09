package com.kw.data.domain.bundle.dto.request

import com.kw.data.common.dto.SearchSortingType

data class BundleSearchCondition(
    val sortingType: SearchSortingType?,
    val tagIds: List<Long>?,
    val keyword: String?
)
