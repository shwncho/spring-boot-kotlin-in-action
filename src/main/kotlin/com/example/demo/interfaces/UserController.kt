package com.example.demo.interfaces

import com.example.demo.application.UserService
import com.example.demo.interfaces.request.UserRequestDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun create(@RequestBody requestDto: UserRequestDto){
        userService.create(requestDto.toRequest())
    }
}