package com.mx.blog.repository

import com.mx.blog.RandomData.generateUser
import com.mx.blog.RandomData.randomString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertNull

@RunWith(SpringRunner::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `should return user by user name when call findByUserName method`() {
        val userName = randomString(10)
        val user = userRepository.save(generateUser(userName = userName))
        userRepository.save(generateUser(userName = randomString(5)))

        userRepository.findAllByUserName(userName).let {
            assertThat(it.size).isEqualTo(1)
            assertThat(it[0]).isEqualTo(user)
        }
    }

    @Test
    fun `should return null when query by wrong password`() {
        val account = randomString()
        val password = randomString()
        userRepository.save(generateUser().apply { this.userAccount = account; this.userPassword = password })

        assertNull(userRepository.findByUserAccountAndUserPassword(account, "wrong_password"))
    }

    @Test
    fun `should return user when query with right password and account`() {
        val account = randomString()
        val password = randomString()
        val user =
            userRepository.save(generateUser().apply { this.userAccount = account; this.userPassword = password })

        assertThat(userRepository.findByUserAccountAndUserPassword(account, password)).isEqualTo(user)
    }

    @Test
    fun `should return null when user has been soft deleted`() {
        var user = userRepository.save(generateUser().apply { this.deleted = true; })

        assertNull(userRepository.findByIdAndDeleted(user.id))
    }

    @Test
    fun `should return user by id when user does not be soft deleted`() {
        var user = userRepository.save(generateUser())

        assertThat(userRepository.findByIdAndDeleted(user.id)).isEqualTo(user)
    }

    @Test
    fun `should return user when user query account is right`() {
        val userAccount = randomString()
        val user = userRepository.save(generateUser().apply { this.userAccount = userAccount })

        assertThat(userRepository.findByUserAccount(userAccount)).isEqualTo(user)
    }
}