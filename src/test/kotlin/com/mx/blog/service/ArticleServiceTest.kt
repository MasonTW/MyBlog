package com.mx.blog.service

import com.mx.blog.DTO.article.ArticleBasicDTO
import com.mx.blog.RandomData.generateArticle
import com.mx.blog.RandomData.generateUser
import com.mx.blog.RandomData.randomString
import com.mx.blog.entity.Article
import com.mx.blog.exception.ArticleIsNotExistedException
import com.mx.blog.repository.AgreementRepository
import com.mx.blog.repository.ArticleRepository
import com.mx.blog.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.util.*
import kotlin.random.Random
import kotlin.test.assertEquals


class ArticleServiceTest {
    private val mockArticleRepository = Mockito.mock(ArticleRepository::class.java)
    private val mockUserRepository = Mockito.mock(UserRepository::class.java)
    private val mockAgreementService = Mockito.mock(AgreementService::class.java)
    private val mockAgreementRepository = Mockito.mock(AgreementRepository::class.java)
    private var usrId = Random.nextLong()
    private var articleId = Random.nextLong()

    private val articleService = ArticleService(mockArticleRepository, mockUserRepository, mockAgreementService,mockAgreementRepository)

    @Test
    fun `should return ArticleInfoDTO when create article successfully`() {
        val articleBasicDTO = ArticleBasicDTO(articleTitle = randomString(), articleContent = randomString())
        val creationTimestamp = randomString()
        val article = generateArticle(usrId).apply {
            this.articleTitle = articleBasicDTO.articleTitle
            this.articleContent = articleBasicDTO.articleContent
            this.articleUpdateTime = creationTimestamp
            this.articleAddTime = creationTimestamp
        }

        `when`(mockArticleRepository.save(any())).thenReturn(article)

        articleService.createArticle(articleBasicDTO, usrId).let {
            assertEquals(articleBasicDTO.articleTitle, it.articleTitle)
            assertEquals(articleBasicDTO.articleContent, it.articleContent)
            assertThat(articleBasicDTO.articleTitle).isEqualTo(it.articleTitle)
        }

    }

    @Test
    fun `should just return articles contain specific username and not be deleted`() {
        val userName = randomString()
        val userId = Random.nextLong()
        val testArticleTitle = randomString()
        val testArticleContent = randomString()
        val apply = mutableListOf<Article>().apply {
            add(generateArticle(userId).apply {
                this.articleTitle = testArticleTitle; this.articleContent = testArticleContent
            })
            add(generateArticle(userId).apply {
                this.deleted = true
            })
        }

        whenever(mockUserRepository.findAllByUserName(any())).thenReturn(listOf(generateUser(userId, userName)))
        whenever(mockArticleRepository.findAllByArticleUserId(userId)).thenReturn(apply)

        articleService.getArticlesByUserName(userName).let {
            assertEquals(1, it.size)
            assertEquals(testArticleTitle, it[0].articleTitle)
            assertEquals(testArticleContent, it[0].articleContent)
        }
    }

    @Test
    fun `should delete article successful when article exists`() {
        val article = generateArticle()
        `when`(mockArticleRepository.findById(articleId)).thenReturn(Optional.of(article))

        assertEquals(true, articleService.deleteArticle(articleId))
    }

    @Test
    fun `should throw exception when delete article but it not exists`() {
        whenever(mockArticleRepository.findById(articleId)).thenReturn(Optional.empty())

        assertThrows<ArticleIsNotExistedException> {
            articleService.deleteArticle(articleId)
        }
    }

    @Test
    fun `should throw exception when get article but it not exists`() {
        whenever(mockArticleRepository.findById(articleId)).thenReturn(Optional.empty())

        assertThrows<ArticleIsNotExistedException> {
            articleService.getArticlesById(articleId)
        }
    }

    @Test
    fun `should throw exception when get article but it has been deleted`() {
        whenever(mockArticleRepository.findById(articleId)).thenReturn(Optional.of(generateArticle().apply { this.deleted = true }))

        assertThrows<ArticleIsNotExistedException> {
            articleService.getArticlesById(articleId)
        }


}
}