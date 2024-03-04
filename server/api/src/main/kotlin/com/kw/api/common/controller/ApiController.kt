package com.kw.api.common.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "공통")
@RestController
class ApiController {

    @GetMapping("/health-check")
    fun healthCheck(): String {
        return "OK"
    }

}
