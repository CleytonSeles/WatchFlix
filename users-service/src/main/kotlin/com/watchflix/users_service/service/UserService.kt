package com.watchflix.users_service.service

import com.watchflix.users_service.model.User
import com.watchflix.users_service.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(user: User): User {
        // Aqui você pode incluir lógica de validação ou criptografia de senha
        return userRepository.save(user)
    }

    fun findUserByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }
}
