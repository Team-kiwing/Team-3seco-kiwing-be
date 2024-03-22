package com.kw.api.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "api")
data class ApiProperties(val hotThreshold: Double)
