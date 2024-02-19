package com.kw.infrasecurity.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    fun filterChain(http : HttpSecurity) : SecurityFilterChain {
        http.invoke {
            csrf { disable() }
            anonymous { disable() }
            formLogin { disable() }
            httpBasic { disable() }
            logout { disable() }
            sessionManagement { SessionCreationPolicy.STATELESS }


            authorizeHttpRequests {
                authorize(anyRequest, permitAll)
            }
        }

        return http.build()
    }
}
