package com.example.demo.interfaces.request

import com.example.demo.application.ProfileRequest

data class ProfileRequestDto(
    val name: String,
    val age: Int,
) {
    fun toRequest(): ProfileRequest {
        return ProfileRequest(name, age)
    }
}
