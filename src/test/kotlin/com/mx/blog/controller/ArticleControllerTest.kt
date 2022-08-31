package com.mx.blog.controller

import com.mx.blog.DTO.ArticleInfoDTO
import com.mx.blog.service.ArticleService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ArticleController::class)
class ArticleControllerTest {

    @Autowired
    private lateinit var mockmvc: MockMvc

    @MockBean
    private lateinit var  article: ArticleService
    private lateinit var articleDTO: ArticleInfoDTO
    private val mockArticleService = mock(ArticleService::class.java)

    @BeforeEach
    fun beforeEach() {
        articleDTO = ArticleInfoDTO(
            articleTitle = "test",
            articleContent = "testContent",
            commentsNum = 0,
            agreementNum = 0,
        )
    }

    @Test
    fun `should return articles when get random articles`(){
       `when`(mockArticleService.getRandomTenArticles()).thenReturn(listOf(articleDTO))

        val request = MockMvcRequestBuilders.get("/article/random")

        mockmvc.perform(request).andExpect(status().isOk)


    }

    @Test
    fun `should return articles when get random articles2`(){
        val request = MockMvcRequestBuilders.post("/article")

       mockmvc.perform(request)
            .andExpect(status().isOk)




    }
 }