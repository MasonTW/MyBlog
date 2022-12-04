package com.mx.blog.repository

import com.mx.blog.RandomData.generateArticle
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.random.Random
import kotlin.test.assertTrue

@RunWith(SpringRunner::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleRepositoryTest {

    @Autowired
    private lateinit var articleRepository: ArticleRepository

    @Test
    fun `should only return not deleted articles when get random articles`() {
        val article1 = articleRepository.save(generateArticle())
        articleRepository.save(generateArticle().apply { this.deleted = true })

        articleRepository.findRandomArticles().let {
            assertThat(it.size).isEqualTo(1)
            assertThat(it[0]).isEqualTo(article1)
        }
    }

    @Test
    fun `should only return not more 10 articles when get random articles`() {
        repeat(Random.nextInt(1, 15)) { articleRepository.save(generateArticle()) }

        assertTrue(articleRepository.findRandomArticles().size <= 10)
    }

    @Test
    fun `should return return right article num when get all articles by user id`() {
        val firstUserArticleNum = Random.nextInt(1, 10)
        val firstUserId = Random.nextLong(1, 100)
        val secondUserArticleNum = Random.nextInt(1, 10)
        val secondUserId = Random.nextLong(1, 100)

        repeat(firstUserArticleNum) {
            articleRepository.save(generateArticle().apply {
                this.articleUserId = firstUserId
            })
        }

        repeat(secondUserArticleNum) {
            articleRepository.save(generateArticle().apply {
                this.articleUserId = secondUserId
            })
        }

        assertThat(articleRepository.findAllByArticleUserId(firstUserId).size).isEqualTo(firstUserArticleNum)
        assertThat(articleRepository.findAllByArticleUserId(secondUserId).size).isEqualTo(secondUserArticleNum)

    }

}