package com.mx.blog.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mx.blog.DTO.User.UserDTO
import com.mx.blog.DTO.conllection.CollectionCreateDTO
import com.mx.blog.DTO.conllection.CollectionsDTO
import com.mx.blog.RandomData.randomString
import com.mx.blog.service.CollectionService
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


@WebMvcTest(CollectionController::class)
class CollectionControllerTest {

    @Autowired
    private lateinit var mockmvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var mockCollectionService: CollectionService

    private var userId: Long = -1L
    private val articleId = Random.nextLong()
    private val collectionId = Random.nextLong()
    private lateinit var userName: String
    private lateinit var token: String
    private lateinit var collectionCreateDTO: CollectionCreateDTO

    @BeforeEach
    fun beforeEach() {
        userId = Random.nextLong()
        userName = randomString()
        token = JWTUtils.getToken(UserDTO(userId, userName))
        collectionCreateDTO =
            CollectionCreateDTO(userId = userId, name = randomString(), articleNum = Random.nextLong())
    }

    @Nested
    inner class CallApiWithoutTokenTests {
        @Test
        fun `should return unauthorized status when get user collection without token`() {
            val request = MockMvcRequestBuilders.get("/users/collections")

            mockmvc.perform(request)
                .andExpect(status().isUnauthorized)

            verify(mockCollectionService, never()).getCollections(any())
        }

        @Test
        fun `should return unauthorized status when create user collection without token`() {
            val request = MockMvcRequestBuilders.post("/users/collections")

            mockmvc.perform(request)
                .andExpect(status().isUnauthorized)

            verify(mockCollectionService, never()).createCollection(any())
        }

        @Test
        fun `should return unauthorized status when add article to collection without token`() {
            val request = MockMvcRequestBuilders.post("/users/collections/articles/$articleId")

            mockmvc.perform(request)
                .andExpect(status().isUnauthorized)

            verify(mockCollectionService, never()).addArticle(any(), any())
        }

        @Test
        fun `should return unauthorized status when get articles of collection without token`() {
            val request = MockMvcRequestBuilders.post("/users/collections/articles")

            mockmvc.perform(request)
                .andExpect(status().isUnauthorized)

            verify(mockCollectionService, never()).getArticles(any())
        }


    }

    @Test
    fun `should get user collection successful`() {
        val request = MockMvcRequestBuilders.get("/users/collections")
            .header("token", token)

        mockmvc.perform(request)
            .andExpect(status().isOk)

        verify(mockCollectionService, times(1)).getCollections(userId)
    }

    @Test
    fun `should create user collection successful`() {
        val request = MockMvcRequestBuilders.post("/users/collections")
            .header("token", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                objectMapper.writeValueAsString(collectionCreateDTO)
            )

        mockmvc.perform(request)
            .andExpect(status().isOk)

        verify(mockCollectionService, times(1)).createCollection(collectionCreateDTO)
    }

    @Test
    fun `should add article to collection successful`() {
        val request = MockMvcRequestBuilders.post("/users/collections/articles/$articleId")
            .header("token", token)
            .param("collectionId", collectionId.toString())

        mockmvc.perform(request)
            .andExpect(status().isOk)

        verify(mockCollectionService, times(1)).addArticle(articleId, collectionId)
    }

    @Test
    fun `should get articles of collection successful`() {
        val request = MockMvcRequestBuilders.get("/users/collections/$collectionId/articles")
            .header("token", token)

        mockmvc.perform(request)
            .andExpect(status().isOk)

        verify(mockCollectionService, times(1)).getArticles(collectionId)
    }
}