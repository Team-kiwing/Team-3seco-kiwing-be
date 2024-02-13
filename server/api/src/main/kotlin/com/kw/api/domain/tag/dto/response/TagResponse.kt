package com.kw.api.domain.tag.dto.response

import com.kw.data.domain.tag.Tag

data class TagResponse(val id : Long?,
        val content : String) {
    companion object {
        fun of(tag: Tag) : TagResponse {
            return TagResponse(id = tag.id,
                    content = tag.content)
        }
    }
}
