package com.example.demo.application

import com.example.demo.domain.User
import com.example.demo.domain.UserRepository
import io.kotest.assertions.any
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import io.mockk.junit5.MockKExtension
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull

@ExtendWith(MockKExtension::class)
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

    Given("찾으려는 유저의 id가 주어지는 경우"){
        val id = 1L

        When("존재하는 유저를 조회하면"){
            val name = "simple"
            val age = 0
            every { userRepository.findByIdOrNull(id) } returns User(name = name, age = age, id = id)

            val userResponse = userService.find(id)
            Then("유저를 정보를 리턴한다."){
                verify(exactly = 1) { userRepository.findByIdOrNull(id) }
                userResponse.id shouldBe id
            }
        }

        When("존재하지 않는 유저를 조회하면"){
            every{ userRepository.findByIdOrNull(any()) }   returns null

            val exception = shouldThrow<EntityNotFoundException> { userService.find(id) }
            Then("예외를 발생시킨다."){
                exception.message shouldBe "존재하지 않는 유저 입니다."
            }
        }
    }

    Given("유저의 id와 수정하려는 profile 정보가 주어지는 경우"){
        val id = 1L
        val request = ProfileRequest(name = "changeName", age = 20)
        When("존재하는 유저라면"){
            val user = User(name = "simple", age = 0, id = id)
            every { userRepository.findByIdOrNull(id) } returns user

            userService.update(id, request)
            Then("프로필이 변경된다."){
                user.name shouldBe "changeName"
                user.age shouldBe 20
            }
        }
        When("존재하지 않는 유저라면"){
            every{ userRepository.findByIdOrNull(any()) }   returns null

            val exception = shouldThrow<EntityNotFoundException> { userService.update(id,request) }
            Then("예외를 발생시킨다."){
                exception.message shouldBe "존재하지 않는 유저 입니다."
            }
        }
    }

    Given("지우려는 유저의 id가 주어지는 경우"){
        val id = 1L
        When("존재하는 유저라면"){
            every { userRepository.deleteById(any()) }  just Runs

            userService.delete(id)
            Then("삭제된다.")
            verify(exactly = 1) { userRepository.deleteById(1L)}
        }
    }
})