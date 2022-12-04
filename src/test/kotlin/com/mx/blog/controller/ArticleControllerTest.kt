package com.mx.blog.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mx.blog.DTO.User.UserDTO
import com.mx.blog.DTO.article.ArticleBasicDTO
import com.mx.blog.DTO.article.ArticleInfoDTO
import com.mx.blog.service.ArticleService
import com.mx.blog.utils.JWTUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.random.Random

@WebMvcTest(ArticleController::class)
class ArticleControllerTest {

    @Autowired
    private lateinit var mockmvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var articleDTO: ArticleInfoDTO
    private lateinit var token: String

    @MockBean
    private lateinit var mockArticleService: ArticleService

    @BeforeEach
    fun beforeEach() {
        articleDTO = ArticleInfoDTO(
            articleTitle = "test",
            articleContent = "testContent",
            commentsNum = 0,
            agreementNum = 0,
            relationship = ArticleInfoDTO.Relationship(isAgreeing = false, isAuthor = false)
        )
        token = JWTUtils.getToken(UserDTO(1, "mason"))
    }

    @Nested
    inner class CallApiWithoutTokenTests {
        private val articleId = Random.nextLong()

        @Test
        fun `should return unauthorized status when create article without token`() {
            val request = MockMvcRequestBuilders.post("/articles")

            mockmvc.perform(request)
                .andExpect(status().isUnauthorized)

            verify(mockArticleService, never()).createArticle(any(), any())
        }

        @Test
        fun `should return unauthorized status when get article without token`() {
            val request = MockMvcRequestBuilders.get("/articles/$articleId")

            mockmvc.perform(request)
                .andExpect(status().isUnauthorized)

            verify(mockArticleService, never()).getArticlesById(any(), any())
        }

        @Test
        fun `should return unauthorized status when delete article without token`() {
            val request = MockMvcRequestBuilders.delete("/articles/$articleId")

            mockmvc.perform(request)
                .andExpect(status().isUnauthorized)

            verify(mockArticleService, never()).deleteArticle(any())
        }

        @Test
        fun `should return unauthorized status when get article by id without token`() {
            val request = MockMvcRequestBuilders.get("/articles/$articleId")

            mockmvc.perform(request)
                .andExpect(status().isUnauthorized)

            verify(mockArticleService, never()).getArticlesById(any(), any())
        }

        @Test
        fun `should return unauthorized status when get article by user name without token`() {
            val request = MockMvcRequestBuilders.get("/articles/testName")

            mockmvc.perform(request)
                .andExpect(status().isUnauthorized)

            verify(mockArticleService, never()).getArticlesByUserName(any())
        }

        @Test
        fun `should return ok status when get random articles successful`() {
            `when`(mockArticleService.getRandomTenArticles()).thenReturn(listOf(articleDTO))

            val request = MockMvcRequestBuilders.get("/articles/random-articles")

            mockmvc.perform(request).andExpect(status().isOk)
        }


    }

    @Test
    fun `should return ok status when create article with token`() {
        val request = MockMvcRequestBuilders.post("/articles")
            .header("token", token)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(ArticleBasicDTO("title", "content")))

        val andExpect = mockmvc.perform(request)
            .andExpect(status().isOk)

        verify(mockArticleService).createArticle(
            ArticleBasicDTO("title", "content"), 1L
        )
    }



    @Test
    fun `should return ok status when update article successful`() {
        val request = MockMvcRequestBuilders.put("/articles/1")
            .header("token", token)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(
                objectMapper.writeValueAsString(ArticleBasicDTO("title", "content"))
            )

        mockmvc.perform(request)
            .andExpect(status().isOk)
    }
}