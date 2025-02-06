package com.watchflix.users_service.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { requests ->
                requests.anyRequest().permitAll()  // Permite todas as requisições
            }
            //.httpBasic(Customizer.withDefaults()) // Comente esta linha para não usar autenticação básica
            .csrf { csrf -> csrf.disable() }       // Desabilita CSRF
        return http.build()
    }
}