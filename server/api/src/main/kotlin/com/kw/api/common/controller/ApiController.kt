package com.kw.api.common.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController {

    @GetMapping("/health-check")
    fun healthCheck(): String {
        return "OK"
    }

}
