package com.mx.blog.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mx.blog.DTO.User.UserDTO
import com.mx.blog.DTO.User.UserLoginDTO
import com.mx.blog.DTO.User.UserRegisterDTO
import com.mx.blog.RandomData.randomString
import com.mx.blog.exception.DuplicatedRegisterException
import com.mx.blog.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.assertEquals


@WebMvcTest(UserController::class)
class UserControllerTest {

    @Autowired
    private lateinit var mockmvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var mockUserService: UserService
    private lateinit var userLoginDTO: UserLoginDTO


    @BeforeEach
    fun beforeEach() {
        userLoginDTO = UserLoginDTO(
            userAccount = randomString(),
            userPassword = randomString(),
        )
    }

    @Test
    fun `should login successful with right account and password`() {
        whenever(mockUserService.login(any())).thenReturn(true)

        val request = MockMvcRequestBuilders
            .post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userLoginDTO))

        mockmvc.perform(request).andExpect(status().isOk).let {
            Assertions.assertEquals("true", it.andReturn().response.contentAsString)
        }

        verify(mockUserService, times(1)).login(userLoginDTO)
    }

    @Test
    fun `should register successful with right info`() {
        val userRegisterDTO = UserRegisterDTO(userName = randomString(), userPassword = randomString(), userAccount = randomString())
        whenever(mockUserService.createUser(userRegisterDTO)).thenReturn(UserDTO(1,"mason"))
        val request = MockMvcRequestBuilders.post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userRegisterDTO))

        mockmvc.perform(request).andExpect(status().isOk).let {
            assertEquals(objectMapper.writeValueAsString(UserDTO(1,"mason")), it.andReturn().response.contentAsString)
        }

        verify(mockUserService, times(1)).createUser(userRegisterDTO)
    }

    @Test
    fun `should register failed when account length more than 10`() {
        val userRegisterDTO = UserRegisterDTO(userName = randomString(), userPassword = randomString(), userAccount = randomString(15))
        whenever(mockUserService.createUser(userRegisterDTO)).thenReturn(UserDTO(1,"mason"))
        val request = MockMvcRequestBuilders.post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userRegisterDTO))

        mockmvc.perform(request).andExpect(status().isBadRequest)

        verify(mockUserService, never()).createUser(userRegisterDTO)
    }

    @Test
    fun `should register failed when duplicated account`() {
        val userRegisterDTO = UserRegisterDTO(userName = randomString(), userPassword = randomString(), userAccount = randomString())
        whenever(mockUserService.createUser(userRegisterDTO)).thenThrow(DuplicatedRegisterException(""))
        val request = MockMvcRequestBuilders.post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userRegisterDTO))

        mockmvc.perform(request).andExpect(status().isUnprocessableEntity)

        verify(mockUserService, times(1)).createUser(userRegisterDTO)
    }
}