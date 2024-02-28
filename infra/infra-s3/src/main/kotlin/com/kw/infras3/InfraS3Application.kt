package com.kw.infras3

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InfraS3Application

fun main(args: Array<String>) {
    runApplication<InfraS3Application>(*args)
}
