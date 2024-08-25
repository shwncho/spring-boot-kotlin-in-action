package com.example.demo.interfaces

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
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields

class UserControllerTest: RestDocsTest() {
    private lateinit var userService: UserService
    private lateinit var userController: UserController

    @BeforeEach
    fun setUp(){
        userService = mockk()
        userController = UserController(userService)
        mockMvc = mockController(userController)
    }

    @Test
    fun createUser() {
        every{ userService.create(any()) } just runs

        given()
            .contentType(ContentType.JSON)
            .body(UserRequestDto("simple",0))
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
                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("user age")
                    )
                )
            )
    }
}