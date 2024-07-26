package com.example.demo.application

import com.example.demo.domain.User
import com.example.demo.domain.UserRepository
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
}