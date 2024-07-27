package com.example.demo.application

import com.example.demo.domain.User

data class UserResponse (
    val id: Long,
    val name: String,
    val age: Int
)

fun User.toUserDto(): UserResponse{
    return UserResponse(
        id = this.id!!,
        name = this.name,
        age = this.age
    )
}