package com.kw.data.common.dto

enum class SearchSortingType {
    //        RECOMMENDED,
    LATEST,
    POPULAR;

    companion object {
        fun from(input: String): SearchSortingType {
            try {
                return SearchSortingType.valueOf(input.uppercase())
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("존재하지 않는 정렬 타입입니다: $input")
            }
        }
    }
}
