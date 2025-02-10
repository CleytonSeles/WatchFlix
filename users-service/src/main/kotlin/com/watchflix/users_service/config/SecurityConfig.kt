package com.watchflix.users_service.config

import com.watchflix.users_service.util.JWTAuthenticationFilter
import com.watchflix.users_service.util.JWTUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(private val jwtUtil: JWTUtil) {

    // Cadeia de segurança para endpoints do Actuator (gerenciamento)
    @Bean
    @Order(1) // Prioridade mais alta
    fun actuatorSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .securityMatcher("/actuator/**") // Aplica somente para requisições que comecem com /actuator/
            .authorizeHttpRequests { it.anyRequest().permitAll() } // Permite todas as requisições
            .csrf { csrf -> csrf.disable() }
        // Não adicionamos o JWTAuthenticationFilter aqui, pois queremos que esses endpoints fiquem livres.
        return http.build()
    }

    // Cadeia de segurança para o restante da aplicação
    @Bean
    @Order(2) // Prioridade mais baixa
    fun appSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .securityMatcher("/**") // Aplica para todas as demais requisições
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { requests ->
                requests
                    // Endpoints públicos da aplicação
                    .requestMatchers("/hello", "/login", "/users").permitAll()
                    // Qualquer outra requisição requer autenticação
                    .anyRequest().authenticated()
            }
            .httpBasic(Customizer.withDefaults())

        // Adiciona o filtro JWT para processar os tokens nas requisições protegidas
        http.addFilterBefore(JWTAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}

