package com.kw.data

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EntityScan("com.kw.data")
@EnableJpaAuditing
@EnableJpaRepositories("com.kw.data")
@EnableTransactionManagement
class DataConfig {
}
