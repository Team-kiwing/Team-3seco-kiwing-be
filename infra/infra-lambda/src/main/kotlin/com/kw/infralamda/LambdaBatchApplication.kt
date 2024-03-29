package com.kw.infralamda

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = arrayOf("com.kw"))
class LambdaBatchApplication

fun main(args: Array<String>) {

    runApplication<LambdaBatchApplication>(*args)
}
