package com.example.demo.application

import com.example.demo.domain.User
import com.example.demo.domain.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun create(request: UserRequest): UserResponse {
        val newUser = User(request.name, request.age)
        return userRepository.save(newUser).toUserDto()

    }

    @Transactional(readOnly = true)
    fun find(id: Long): UserResponse {
        return userRepository.findByIdOrNull(id)?.toUserDto()
            ?: throw EntityNotFoundException("존재하지 않는 유저 입니다.")
    }

    @Transactional
    fun update(
        id: Long,
        profileRequest: ProfileRequest,
    ) {
        val user =
            userRepository.findByIdOrNull(id)
                ?: throw EntityNotFoundException("존재하지 않는 유저 입니다.")
        user.update(profileRequest.name, profileRequest.age)
    }

    @Transactional
    fun delete(id: Long) {
        userRepository.deleteById(id)
    }
}
