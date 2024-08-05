package com.example.demo.interfaces.request

import com.example.demo.application.UserRequest

data class UserRequestDto(
    val name: String,
    val age: Int,
) {
    fun toRequest(): UserRequest {
        return UserRequest(name, age)
    }
}
