package com.example.demo.interfaces

import com.example.demo.application.UserResponse
import com.example.demo.application.UserService
import com.example.demo.interfaces.request.UserRequestDto
import com.example.demo.support.RestDocsTest
import com.example.demo.support.RestDocsUtils.requestPreprocessor
import com.example.demo.support.RestDocsUtils.responsePreprocessor
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.restassured.http.ContentType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters

class UserControllerTest : RestDocsTest() {
    private lateinit var userService: UserService
    private lateinit var userController: UserController

    @BeforeEach
    fun setUp() {
        userService = mockk()
        userController = UserController(userService)
        mockMvc = mockController(userController)
    }

    @Test
    fun createUser() {
        every { userService.create(any()) } just runs

        given()
            .contentType(ContentType.JSON)
            .body(UserRequestDto("simple", 0))
            .post("/api/v1/users")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "createUser",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("user name"),
                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("user age"),
                    ),
                ),
            )
    }

    @Test
    fun findUser() {
        every { userService.find(any()) } returns UserResponse(1L, "simple", 0)

        given()
            .contentType(ContentType.JSON)
            .pathParam("id", 1L)
            .get("/api/v1/users/{id}")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "findUser",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    pathParameters(
                        parameterWithName("id").description("user id"),
                    ),
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("user id"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("user name"),
                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("user age"),
                    ),
                ),
            )
    }
}
