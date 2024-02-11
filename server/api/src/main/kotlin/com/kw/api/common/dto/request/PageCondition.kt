package com.kw.api.common.dto.request

data class PageCondition(
    val page: Int? = DEFAULT_PAGE,
    val size: Int? = DEFAULT_SIZE
) {
    companion object {
        private const val DEFAULT_PAGE = 1
        private const val DEFAULT_SIZE = 10
    }

    init {
        require(isValidPage(page)) { "Invalid page value: $page" }
        require(isValidSize(size)) { "Invalid size value: $size" }
    }

    private fun isValidPage(page: Int?): Boolean {
        return page != null && page > 0
    }

    private fun isValidSize(size: Int?): Boolean {
        return size != null && size > 0
    }
}
