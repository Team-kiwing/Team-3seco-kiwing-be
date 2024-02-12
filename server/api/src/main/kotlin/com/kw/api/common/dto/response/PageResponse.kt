package com.kw.api.common.dto.response

import org.springframework.data.domain.Page

data class PageResponse<T>(
    val totalPages: Int,
    val currentPage: Int,
    val pageSize: Int,
    val content: MutableList<T>
) {
    companion object {
        fun <T> of(page: Page<T>): PageResponse<T> {
            return PageResponse(
                totalPages = page.totalPages,
                currentPage = page.pageable.pageNumber + 1,
                pageSize = page.pageable.pageSize,
                content = page.content
            )
        }
    }
}
