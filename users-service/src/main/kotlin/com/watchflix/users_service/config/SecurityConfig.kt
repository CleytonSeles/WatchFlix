package com.watchflix.users_service.config

import com.watchflix.users_service.util.JWTUtil
import io.jsonwebtoken.Claims
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class JWTAuthenticationFilter(private val jwtUtil: JWTUtil) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // Adicione a condição de bypass para endpoints públicos
        if (request.requestURI.startsWith("/actuator/") ||
            request.requestURI == "/hello" ||
            request.requestURI == "/login" ||
            request.requestURI == "/users") {
            // Esses endpoints já estão configurados para serem públicos;
            // passe a requisição adiante sem processar o token
            filterChain.doFilter(request, response)
            return
        }

        // Caso a URI não seja um endpoint público, prossiga com a verificação do JWT.
        val authHeader = request.getHeader("Authorization")
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)
            val claims: Claims? = jwtUtil.getClaims(token)

            if (claims != null) {
                val username = claims.subject
                if (username != null && SecurityContextHolder.getContext().authentication == null) {
                    // Cria um objeto de autenticação básico
                    val authToken = UsernamePasswordAuthenticationToken(username, null, listOf())
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }
        }
        // Continue a cadeia de filtros
        filterChain.doFilter(request, response)
    }
}
