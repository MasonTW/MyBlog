package com.mx.blog.service

import com.mx.blog.DTO.User.UserRegisterDTO
import com.mx.blog.entity.User
import com.mx.blog.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock


class ArticleServiceTest {
    private val mockUserRepository = mock(UserRepository::class.java)

    @Test
    fun `should return user entity when create article successfully`() {
        val user = User(
            id = 1,
            userAccount = "testAccount",
            userName = "testUser",
            userPassword = "12345",
            userRegisterTime = "now"
        )
        val userService = UserService(mockUserRepository)
        `when`(mockUserRepository.save(any())).thenReturn(user)

        val result = userService.createUser(
            UserRegisterDTO(
                userName = "testUser",
                userAccount = "testAccount",
                userPassword = "12345"
            )
        )

//        Assertions.assertEquals(user.id, )
//        Assertions.assertEquals(user.userAccount, result.a)
    }

}