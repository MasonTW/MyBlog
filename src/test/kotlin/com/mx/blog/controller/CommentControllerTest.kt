package com.mx.blog.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mx.blog.DTO.User.UserDTO
import com.mx.blog.RandomData.randomString
import com.mx.blog.service.CommentService
import com.mx.blog.utils.JWTUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.random.Random


@WebMvcTest(CommentController::class)
class CommentControllerTest {

    @Autowired
    private lateinit var mockmvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var mockCommentService: CommentService

    private var userId: Long = -1L
    private lateinit var userName: String
    private lateinit var token: String

    @BeforeEach
    fun beforeEach() {
        userId = Random.nextLong()
        userName = randomString()
        token = JWTUtils.getToken(UserDTO(userId, userName))
    }

    @Nested
    inner class CallApiWithoutTokenTests {
        @Test
        fun `should return unauthorized status when comment article without token`() {
            val request = MockMvcRequestBuilders.post("/articles/${Random.nextLong()}/comments")

            mockmvc.perform(request)
                .andExpect(status().isUnauthorized)

            verify(mockCommentService, never()).addComment(any(), any(), any())
        }

        @Test
        fun `should return unauthorized status when delete article comment without token`() {
            val request = MockMvcRequestBuilders.delete("/comments/${Random.nextLong()}")

            mockmvc.perform(request)
                .andExpect(status().isUnauthorized)

            verify(mockCommentService, never()).deleteComment(any())
        }

        @Test
        fun `should return unauthorized status when get article comment without token`() {
            val request = MockMvcRequestBuilders.get("/articles/comments/${Random.nextLong()}")

            mockmvc.perform(request)
                .andExpect(status().isUnauthorized)

            verify(mockCommentService, never()).getArticleComments(any())
        }

    }

    @Test
    fun `should add comment successful`() {
        val articleId = Random.nextLong()
        val comment = randomString()
        val request = MockMvcRequestBuilders.post("/articles/$articleId/comments")
            .header("token", token)
            .param("comment",comment)

        mockmvc.perform(request)
            .andExpect(status().isOk)

        verify(mockCommentService, times(1)).addComment(comment,articleId,userId)
    }

    @Test
    fun `should delete comment successful`(){
        val commentId = Random.nextLong()
        val request = MockMvcRequestBuilders.delete("/comments/$commentId")
            .header("token", token)

        mockmvc.perform(request)
            .andExpect(status().isOk)

        verify(mockCommentService, times(1)).deleteComment(commentId)
    }

    @Test
    fun `should get article comments successful`(){
        val articleId = Random.nextLong()
        val request = MockMvcRequestBuilders.get("/articles/$articleId/comments/")
            .header("token", token)

        mockmvc.perform(request)
            .andExpect(status().isOk)

        verify(mockCommentService, times(1)).getArticleComments(any())
    }


}