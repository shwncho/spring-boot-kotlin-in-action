package com.example.demo.application

import com.example.demo.domain.User
import com.example.demo.domain.UserRepository
import io.kotest.assertions.any
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class UserServiceTest: BehaviorSpec({
    val userRepository = mockk<UserRepository>()
    val userService = UserService(userRepository)

    Given("유저의 이름과 나이가 주어지는 경우") {
        val userRequest = UserRequest(name = "simple", age = 0)

        When("유저가 등록하면") {
            every { userRepository.save(any()) }   returns User(name = "simple", age = 0, id = 1L)

            val savedUser = userService.create(userRequest)
            Then("저장이 된다.") {
                verify(exactly = 1) { userRepository.save(any()) }
                savedUser.name shouldBe "simple"
                savedUser.age shouldBe 0
            }
        }
    }
})