package com.watchflix.users_service.controller

import com.watchflix.users_service.model.User
import com.watchflix.users_service.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: UserService) {

    @GetMapping("/hello")
    fun sayHello(): String {
        return "Olá, WatchFlix!"
    }

    // Endpoint temporário para criar um usuário
    @PostMapping("/users")
    fun createUser(@RequestBody user: User): User {
        return userService.createUser(user)
    }
}
