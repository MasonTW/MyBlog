package com.mx.blog.repository

import com.mx.blog.RandomData.generateUser
import com.mx.blog.RandomData.randomString
import com.mx.blog.entity.Agreement
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.random.Random

@RunWith(SpringRunner::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AgreementRepositoryTest {

    @Autowired
    private lateinit var agreementRepository: AgreementRepository

    @Test
    fun `should return agreement by article id`() {
        val articleId = Random.nextLong()
        val agreement = agreementRepository.save(
            Agreement(
                agreementId = Random.nextLong(),
                articleId = articleId,
                agreementNum = Random.nextLong()
            )
        )

        agreementRepository.findByArticleId(articleId).let {
            assertThat(it).isEqualTo(agreement)
        }
    }

}