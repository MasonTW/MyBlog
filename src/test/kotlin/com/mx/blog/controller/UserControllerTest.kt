package com.mx.blog.controller

import com.mx.blog.DTO.UserLoginDTO
import com.mx.blog.service.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(UserController::class)
class UserControllerTest() {

    @Autowired
    private lateinit var mockmvc: MockMvc

    @MockBean
    private lateinit var  userService: UserService
    private lateinit var userDTO: UserLoginDTO


    @BeforeEach
    fun beforeEach() {
        userDTO = UserLoginDTO(
            userAccount = "123",
            userPassword = "123",
        )
    }

    @Test
    fun `should return articles when get random articles`() {

        val request = MockMvcRequestBuilders.post("/login", userDTO)

        mockmvc.perform(request).andExpect(status().isOk)


    }

    @Test
    fun `should return articles when get random articles2`() {
        val request = MockMvcRequestBuilders.post("/register")

        mockmvc.perform(request).andExpect(status().isOk)


    }

    @Test
    fun `should return articles when get random articles3`() {
        val request = MockMvcRequestBuilders.post("/user/{id}", 123)
        mockmvc.perform(request).andExpect(status().isOk)


    }
}