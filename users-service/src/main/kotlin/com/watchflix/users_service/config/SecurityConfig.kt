package com.watchflix.users_service.config

import com.watchflix.users_service.util.JWTAuthenticationFilter
import com.watchflix.users_service.util.JWTUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(private val jwtUtil: JWTUtil) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/hello", "/login").permitAll() // endpoints públicos
                    .anyRequest().authenticated()
            }
            .httpBasic(Customizer.withDefaults())

        // Adiciona o JWTAuthenticationFilter antes do UsernamePasswordAuthenticationFilter
        http.addFilterBefore(JWTAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
