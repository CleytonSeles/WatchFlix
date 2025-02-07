package com.watchflix.users_service.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JWTUtil {
    // A chave secreta agora será gerada apenas uma vez para a instância gerenciada pelo Spring.
    private val secretKey: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    // Tempo de expiração do token (por exemplo, 10 horas)
    private val expirationTimeInMs: Long = 10 * 60 * 60 * 1000

    // Gera um token JWT com base no nome de usuário (pode incluir outras informações se necessário)
    fun generateToken(username: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expirationTimeInMs)

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(secretKey)
            .compact()
    }

    // Extrai as informações (claims) contidas no token
    fun getClaims(token: String): Claims? {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (ex: Exception) {
            null
        }
    }

    // Valida se o token é válido e não expirou
    fun isTokenValid(token: String, username: String): Boolean {
        val claims = getClaims(token)
        if (claims != null) {
            val tokenUsername = claims.subject
            val expiration = claims.expiration
            val now = Date()
            return (tokenUsername == username && now.before(expiration))
        }
        return false
    }
}
