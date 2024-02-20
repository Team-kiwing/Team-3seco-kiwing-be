package com.kw.infrasecurity.config

import com.kw.infrasecurity.jwt.JwtAccessDeniedHandler
import com.kw.infrasecurity.jwt.JwtAuthenticationEntryPoint
import com.kw.infrasecurity.jwt.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig(val jwtAuthenticationFilter: JwtAuthenticationFilter,
    val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint) {

    @Bean
    fun filterChain(http : HttpSecurity) : SecurityFilterChain {
        http.invoke {
            csrf { disable() }
            anonymous { disable() }
            formLogin { disable() }
            httpBasic { disable() }
            logout { disable() }
            sessionManagement { SessionCreationPolicy.STATELESS }

            addFilterAfter<LogoutFilter>(jwtAuthenticationFilter)
            exceptionHandling {
                authenticationEntryPoint = jwtAuthenticationEntryPoint
                accessDeniedHandler = jwtAccessDeniedHandler
            }

            authorizeHttpRequests {
                authorize(anyRequest, permitAll)
            }
        }

        return http.build()
    }
}
