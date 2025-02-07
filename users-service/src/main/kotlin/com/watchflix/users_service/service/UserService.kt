package com.watchflix.users_service.service

import com.watchflix.users_service.model.User
import com.watchflix.users_service.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder  // Injetado via Spring
) {

    fun createUser(user: User): User {
        // Criptografa a senha antes de salvar
        val encryptedPassword = passwordEncoder.encode(user.password)
        // Cria uma nova instância de usuário com a senha criptografada (usando a função copy do data class)
        val userToSave = user.copy(password = encryptedPassword)
        return userRepository.save(userToSave)
    }

    fun findUserByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }
}

