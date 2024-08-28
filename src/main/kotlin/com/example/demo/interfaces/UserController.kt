package com.example.demo.interfaces

import com.example.demo.application.UserService
import com.example.demo.interfaces.request.ProfileRequestDto
import com.example.demo.interfaces.request.UserRequestDto
import com.example.demo.interfaces.response.UserResponseDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
) {
    @PostMapping
    fun create(
        @RequestBody requestDto: UserRequestDto,
    ): UserResponseDto{
        val userResponse = userService.create(requestDto.toRequest())
        return UserResponseDto(userResponse.id, userResponse.name, userResponse.age)
    }

    @GetMapping("{id}")
    fun find(
        @PathVariable id: Long,
    ): UserResponseDto {
        val userResponse = userService.find(id)
        return UserResponseDto(userResponse.id, userResponse.name, userResponse.age)
    }

    @PatchMapping("{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody profileRequestDto: ProfileRequestDto,
    ) {
        userService.update(id, profileRequestDto.toRequest())
    }

    @DeleteMapping("{id}")
    fun delete(
        @PathVariable id: Long,
    ) {
        userService.delete(id)
    }
}
