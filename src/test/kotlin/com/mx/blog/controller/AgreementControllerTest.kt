package com.mx.blog.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mx.blog.DTO.User.UserDTO
import com.mx.blog.RandomData.randomString
import com.mx.blog.service.AgreementService
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
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.random.Random


@WebMvcTest(AgreementController::class)
class AgreementControllerTest {

    @Autowired
    private lateinit var mockmvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var mockAgreementService: AgreementService

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
        fun `should return unauthorized status when agree article without token`() {
            val request = MockMvcRequestBuilders.post("/articles/${Random.nextLong()}/agreements")

            mockmvc.perform(request)
                .andExpect(status().isUnauthorized)

            verify(mockAgreementService, never()).agreeArticle(any(), any())
        }

        @Test
        fun `should return unauthorized status when cancel article without token`() {
            val request = MockMvcRequestBuilders.post("/articles/${Random.nextLong()}/agreements")

            mockmvc.perform(request)
                .andExpect(status().isUnauthorized)

            verify(mockAgreementService, never()).cancelArticleAgreement(any(), any())
        }

        @Test
        fun `should return unauthorized status when get agreement without token`() {
            val request = MockMvcRequestBuilders.get("/articles/${Random.nextLong()}/agreements")

            mockmvc.perform(request)
                .andExpect(status().isUnauthorized)

            verify(mockAgreementService, never()).isAgreed(any(), any())
        }

        @Test
        fun `should return unauthorized status when get agreement records without token`() {
            val request = MockMvcRequestBuilders.get("/users/agreements")

            mockmvc.perform(request)
                .andExpect(status().isUnauthorized)

            verify(mockAgreementService, never()).getAgreementRecords(any())
        }
    }

    @Test
    fun `should agree article successful`() {
        val articleId = Random.nextLong()
        val request = MockMvcRequestBuilders.post("/articles/$articleId/agreements")
            .header("token", token)

        mockmvc.perform(request)
            .andExpect(status().isOk)

        verify(mockAgreementService, times(1)).agreeArticle(articleId, userId)
    }

    @Test
    fun `should cancel article agreement successful`() {
        val articleId = Random.nextLong()
        val request = MockMvcRequestBuilders.delete("/articles/$articleId/agreements")
            .header("token", token)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        mockmvc.perform(request)
            .andExpect(status().isOk)

        verify(mockAgreementService, times(1)).cancelArticleAgreement(articleId, userId)
    }

    @Test
    fun `should get article agreement successful`() {
        val articleId = Random.nextLong()
        val request = MockMvcRequestBuilders.get("/articles/$articleId/agreements")
            .header("token", token)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        mockmvc.perform(request)
            .andExpect(status().isOk)

        verify(mockAgreementService, times(1)).isAgreed(articleId, userId)
    }

    @Test
    fun `should get user agreement records successful`() {
        val request = MockMvcRequestBuilders.get("/users/agreements")
            .header("token", token)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        mockmvc.perform(request)
            .andExpect(status().isOk)

        verify(mockAgreementService, times(1)).getAgreementRecords(userId)
    }
}