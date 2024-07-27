package com.example.demo.application

import com.example.demo.domain.User
import com.example.demo.domain.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserService(
    private val userRepository: UserRepository
){

    @Transactional
    fun create(request: UserRequest){
        val createdUser = User(request.name, request.age)
        userRepository.save(createdUser)
    }

    @Transactional(readOnly = true)
    fun find(id: Long): UserResponse{
        return userRepository.findByIdOrNull(id)?.toUserDto()
            ?: throw EntityNotFoundException("존재하지 않는 유저 입니다.")
    }
}