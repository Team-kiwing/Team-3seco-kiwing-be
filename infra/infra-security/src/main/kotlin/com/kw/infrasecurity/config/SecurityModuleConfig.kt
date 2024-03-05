package com.kw.infrasecurity.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories


@Configuration
@EnableRedisRepositories(basePackages = ["com.kw"])
@ComponentScan(basePackages = ["com.kw"])
class SecurityModuleConfig


