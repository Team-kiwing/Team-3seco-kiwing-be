package com.kw.api.domain.tag.dto.response

import com.kw.data.domain.tag.Tag

data class TagResponse(
    val id: Long,
    val name: String
) {
    companion object {
        fun from(tag: Tag): TagResponse {
            return TagResponse(
                id = tag.id!!,
                name = tag.name
            )
        }
    }
}
