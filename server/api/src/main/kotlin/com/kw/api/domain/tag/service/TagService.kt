package com.kw.api.domain.tag.service

import com.kw.api.domain.tag.dto.response.TagResponse
import com.kw.data.domain.tag.repository.TagRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class TagService(val tagRepository: TagRepository) {
    fun getTags() : List<TagResponse> {
        val tags = tagRepository.findAll()
        return tags.map { tag ->
            TagResponse.from(tag)
        }
    }
}
