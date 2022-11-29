package com.mx.blog.repository

import com.mx.blog.RandomData.generateUser
import com.mx.blog.RandomData.randomString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `should return user by user name when call findByUserName method`(){
        val userName = randomString(10)
        val user = userRepository.save(generateUser(userName = userName))
        userRepository.save(generateUser(userName = randomString(5)))

        userRepository.findAllByUserName(userName).let {
            assertThat(it.size).isEqualTo(1)
            assertThat(it[0]).isEqualTo(user)
        }
    }
}