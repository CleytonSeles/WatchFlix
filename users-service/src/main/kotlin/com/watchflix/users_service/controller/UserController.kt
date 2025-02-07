package com.watchflix.users_service.controller

import com.watchflix.users_service.model.User
import com.watchflix.users_service.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder

@RestController
class UserController(private val userService: UserService) {

    @GetMapping("/hello")
    fun sayHello(): String {
        return "Olá, WatchFlix!"
    }

    @GetMapping("/profile")
    fun getProfile(): ResponseEntity<Any> {
        // Recupera o objeto de autenticação do contexto do Spring Security
        val auth = SecurityContextHolder.getContext().authentication

        // Verifica se há um usuário autenticado
        if (auth == null || auth.name.isNullOrEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(mapOf("error" to "Usuário não autenticado"))
        }

        // Usa o nome de usuário (obtido do token JWT) para buscar os dados completos do usuário
        val user = userService.findUserByUsername(auth.name)

        return if (user != null) {
            // Retorna os dados do usuário (a senha não deve ser exposta em produção; aqui é um exemplo simples)
            ResponseEntity.ok(
                mapOf(
                    "id" to user.id,
                    "username" to user.username,
                    "email" to user.email
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf("error" to "Usuário não encontrado"))
        }
    }


    // Endpoint temporário para criar um usuário
    @PostMapping("/users")
    fun createUser(@RequestBody user: User): User {
        return userService.createUser(user)
    }
}
