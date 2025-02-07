package com.watchflix.users_service.util

import io.jsonwebtoken.Claims
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JWTAuthenticationFilter(private val jwtUtil: JWTUtil) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // Recupera o cabeçalho "Authorization" da requisição
        val authHeader = request.getHeader("Authorization")

        // Verifica se o cabeçalho está presente e se começa com "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)
            val claims: Claims? = jwtUtil.getClaims(token)

            if (claims != null) {
                // Recupera o nome do usuário do token
                val username = claims.subject

                // Se o token for válido e não houver autenticação configurada no contexto, cria a autenticação
                if (username != null && SecurityContextHolder.getContext().authentication == null) {
                    // Aqui estamos criando uma autenticação simples, sem carregar detalhes adicionais de roles
                    val authToken = UsernamePasswordAuthenticationToken(username, null, listOf())
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                    // Define o usuário autenticado no contexto do Spring Security
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }
        }

        // Prossegue com a cadeia de filtros
        filterChain.doFilter(request, response)
    }
}
