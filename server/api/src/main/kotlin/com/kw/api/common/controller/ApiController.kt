package com.kw.api.common.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ApiController {

    @GetMapping("/health-check")
    fun healthCheck(): String {
        return "OK"
    }

}
