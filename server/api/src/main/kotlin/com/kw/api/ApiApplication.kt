package com.kw.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.kw")
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
