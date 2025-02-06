package com.watchflix.users_service.controller

import com.watchflix.users_service.dto.LoginRequest
import com.watchflix.users_service.model.User
import com.watchflix.users_service.service.UserService
import com.watchflix.users_service.util.JWTUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val userService: UserService,
    private val jwtUtil: JWTUtil = JWTUtil()  // Em produção, considere injetar via Spring
) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        // Busca o usuário pelo nome de usuário
        val user: User? = userService.findUserByUsername(loginRequest.username)

        // Verifica se o usuário existe e se a senha está correta
        if (user == null || user.password != loginRequest.password) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(mapOf("error" to "Credenciais inválidas"))
        }

        // Gera o token JWT
        val token = jwtUtil.generateToken(user.username)

        // Retorna o token na resposta
        return ResponseEntity.ok(mapOf("token" to token))
    }
}
