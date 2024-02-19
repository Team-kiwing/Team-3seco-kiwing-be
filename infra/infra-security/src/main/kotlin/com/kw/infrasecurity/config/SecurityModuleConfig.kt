package com.kw.infrasecurity.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration


@Configuration
//@EnableRedisRepositories(basePackages = "com.kw")
@ComponentScan(basePackages = ["com.kw"])
class SecurityModuleConfig


