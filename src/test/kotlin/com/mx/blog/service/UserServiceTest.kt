package com.mx.blog.service

import com.mx.blog.DTO.User.UserLoginDTO
import com.mx.blog.DTO.User.UserRegisterDTO
import com.mx.blog.RandomData.generateUser
import com.mx.blog.RandomData.randomString
import com.mx.blog.entity.User
import com.mx.blog.exception.DuplicatedRegisterException
import com.mx.blog.exception.NoSuchUserException
import com.mx.blog.repository.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
class UserServiceTest {
    @Mock
    private lateinit var mockUserRepository: UserRepository

    @InjectMocks
    private lateinit var userService: UserService

    private val userId = Random.nextLong()
    private val userName = randomString()

    @Test
    fun `should return right user dto when user exists`() {
        whenever(mockUserRepository.findByIdAndDeleted(userId)).thenReturn(generateUser(userId, userName))

        userService.findUserById(userId).let {
            assertEquals(userId, it.userId)
            assertEquals(userName, it.userName)
        }
    }

    @Test
    fun `should throw NotSuchUserException when user does not exist or be deleted`() {
        whenever(mockUserRepository.findByIdAndDeleted(userId)).thenReturn(null)

        assertThrows<NoSuchUserException> {
            userService.findUserById(userId)
        }
    }

    @Test
    fun `should create user successful when register info is right`() {
        val userRegisterDTO = UserRegisterDTO(userName, randomString(), randomString())
        val user = User.toUser(userRegisterDTO)
        `when`(mockUserRepository.save(any())).thenReturn(user.apply { this.id = userId })

        userService.createUser(userRegisterDTO).let {
            assertEquals(userRegisterDTO.userName, it.userName)
            assertEquals(userId, it.userId)
        }
    }

    @Test
    fun `should throw DuplicatedRegisterException when register info is duplicated `() {
        val userRegisterDTO = UserRegisterDTO(userName, randomString(), randomString())
        whenever(mockUserRepository.findByUserAccount(userRegisterDTO.userAccount)).thenReturn(
            generateUser()
        )

        assertThrows<DuplicatedRegisterException> {
            userService.createUser(userRegisterDTO)
        }
    }

    @Test
    fun `should login successful when login info is right`() {
        val userLoginDTO = UserLoginDTO(randomString(), randomString())
        whenever(
            mockUserRepository.findByUserAccountAndUserPassword(
                userLoginDTO.userAccount,
                userLoginDTO.userPassword
            )
        ).thenReturn(
            generateUser()
        )
        assertTrue(userService.login(userLoginDTO))
    }

    @Test
    fun `should login failed when login info is wrong`(){
        val userLoginDTO = UserLoginDTO(randomString(), randomString())
        whenever(
            mockUserRepository.findByUserAccountAndUserPassword(
                userLoginDTO.userAccount,
                userLoginDTO.userPassword
            )
        ).thenReturn(
            null
        )
        assertFalse(userService.login(userLoginDTO))
    }

}