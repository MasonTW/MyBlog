package com.mx.blog.service

import com.mx.blog.DTO.ArticleCreateDTO
import com.mx.blog.DTO.ArticleGetDTO
import com.mx.blog.entity.Article
import com.mx.blog.repository.ArticleRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.servlet.http.HttpSession

@Service
class ArticleService(
    private val articleRepository: ArticleRepository
) {
    fun createArticle(articleGetDTO: ArticleCreateDTO, session: HttpSession): Article {
        val newArticle = Article(
            articleTitle = articleGetDTO.articleTitle,
            articleContent = articleGetDTO.articleContent,
            articleAddTime = LocalDateTime.now().toString(),
            articleUserId = session.getAttribute("userId") as Long
        )
        return articleRepository.save(newArticle)
    }
}
