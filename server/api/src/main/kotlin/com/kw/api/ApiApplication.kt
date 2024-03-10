package com.kw.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = arrayOf("com.kw"))
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
