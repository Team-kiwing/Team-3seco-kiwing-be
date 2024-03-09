package com.kw.api.domain.tag.controller

import com.kw.api.domain.tag.dto.response.TagResponse
import com.kw.api.domain.tag.service.TagService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "태그")
@RestController
@RequestMapping("/api/v1")
class TagController(val tagService: TagService) {

    @Operation(summary = "태그 목록 조회")
    @GetMapping("/tags")
    fun getTags(): List<TagResponse> {
        return tagService.getTags()
    }
}
