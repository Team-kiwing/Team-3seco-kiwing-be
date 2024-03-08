package com.kw.infrasecurity.config

import com.kw.infrasecurity.jwt.JwtAccessDeniedHandler
import com.kw.infrasecurity.jwt.JwtAuthenticationEntryPoint
import com.kw.infrasecurity.jwt.JwtAuthenticationFilter
import com.kw.infrasecurity.oauth.OAuth2SuccessHandler
import com.kw.infrasecurity.oauth.OAuth2UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.*
import org.springframework.security.config.annotation.web.AuthorizeHttpRequestsDsl
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    val jwtAuthenticationFilter: JwtAuthenticationFilter,
    val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    val oAuth2SuccessHandler: OAuth2SuccessHandler,
    val oAuth2UserService: OAuth2UserService,
    @Value("\${allowed-origins}") private val allowedOrigins: String,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.invoke {
            headers { frameOptions { disable() } }
            csrf { disable() }
            anonymous { disable() }
            formLogin { disable() }
            httpBasic { disable() }
            logout { disable() }
            sessionManagement { SessionCreationPolicy.STATELESS }

            cors {
                configurationSource = corsConfigurationSource()
            }

            addFilterAfter<LogoutFilter>(jwtAuthenticationFilter)
            exceptionHandling {
                authenticationEntryPoint = jwtAuthenticationEntryPoint
                accessDeniedHandler = jwtAccessDeniedHandler
            }

            oauth2Login {
                authenticationSuccessHandler = oAuth2SuccessHandler
                userInfoEndpoint {
                    userService = oAuth2UserService
                }
            }

            authorizeHttpRequests {
                authorize(CorsUtils::isPreFlightRequest, permitAll)

//                bundleRequests()
//                claimRequests()
//                tagRequests()
//                questionRequests()
//                memberRequests()

                authorize(anyRequest, permitAll)
            }
        }

        return http.build()
    }

    private fun AuthorizeHttpRequestsDsl.memberRequests() {
        authorize(GET, "/api/v1/members/me", hasRole("USER"))
        authorize(PATCH, "/api/v1/members/me/nickname", hasRole("USER"))
        authorize(PATCH, "/api/v1/members/me/sns", hasRole("USER"))
        authorize(PATCH, "/api/v1/members/me/tags", hasRole("USER"))
        authorize(PATCH, "/api/v1/members/me", hasRole("USER"))
        authorize(PATCH, "/api/v1/members/me/profile-image", hasRole("USER"))

        authorize(GET, "/api/v1/members/{id}", permitAll)
    }

    private fun AuthorizeHttpRequestsDsl.questionRequests() {
        authorize(POST, "/api/*/questions", hasRole("USER"))
        authorize(PATCH, "/api/*/questions/{id}", hasRole("USER"))
        authorize(DELETE, "/api/*/questions/{id}", hasRole("USER"))
        authorize(POST, "/api/*/questions/{id}/report", hasRole("USER"))

        authorize(GET, "/api/*/questions/search", permitAll)
    }

    private fun AuthorizeHttpRequestsDsl.tagRequests() {
        authorize(GET, "/api/*/tags", permitAll)
    }

    private fun AuthorizeHttpRequestsDsl.claimRequests() {
        authorize(POST, "/api/*/claims", hasRole("USER"))
    }

    private fun AuthorizeHttpRequestsDsl.bundleRequests() {
        authorize(POST, "/api/*/bundles", hasRole("USER"))
        authorize(POST, "/api/*/bundles", hasRole("USER"))
        authorize(PATCH, "/api/*/bundles/bundle-order", hasRole("USER"))
        authorize(GET, "/api/*/bundles/my", hasRole("USER"))
        authorize(GET, "/api/*/bundles/{id}", hasRole("USER"))
        authorize(PATCH, "/api/*/bundles/{id}", hasRole("USER"))
        authorize(DELETE, "/api/*/bundles/{id}", hasRole("USER"))
        authorize(POST, "/api/*/bundles/{id}/scrape", hasRole("USER"))
        authorize(PATCH, "/api/*/bundles/{id}/question-order", hasRole("USER"))
        authorize(POST, "/api/*/bundles/questions", hasRole("USER"))
        authorize(DELETE, "/api/*/bundles/{id}/questions", hasRole("USER"))

        authorize(GET, "/api/*/bundles/search", permitAll)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        configuration.allowedOriginPatterns = allowedOrigins.split(",").map { it.trim() }
        configuration.allowCredentials = true
        configuration.allowedHeaders = listOf("*")
        configuration.allowedMethods = listOf("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
