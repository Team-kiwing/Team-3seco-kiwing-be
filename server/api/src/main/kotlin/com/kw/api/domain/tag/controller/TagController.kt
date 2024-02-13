package com.kw.api.domain.tag.controller

import com.kw.api.common.dto.ApiResponse
import com.kw.api.domain.tag.dto.response.TagResponse
import com.kw.api.domain.tag.service.TagService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class TagController(val tagService: TagService) {
    @GetMapping("/tags")
    fun getTags() : ApiResponse<List<TagResponse>> {
        val responses = tagService.getTags()
        return ApiResponse.ok(responses)
    }
}
