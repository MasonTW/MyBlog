package com.mx.blog.service

import com.mx.blog.DTO.ArticleCreateDTO
import com.mx.blog.entity.Article
import com.mx.blog.repository.ArticleRepository
import com.mx.blog.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.servlet.http.HttpSession

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository,
) {
    fun createArticle(articleGetDTO: ArticleCreateDTO, session: HttpSession): Article {
        val newArticle = Article(
            articleTitle = articleGetDTO.articleTitle,
            articleContent = articleGetDTO.articleContent,
            articleAddTime = System.currentTimeMillis().toString(),
            articleUpdateTime = System.currentTimeMillis().toString(),
            articleUserId = session.getAttribute("userId") as Long,
        )
        return articleRepository.save(newArticle)
    }

    fun getArticlesByUserName(userName: String): List<Article> {
        val userId = userRepository.findByUserName(userName).id
        return articleRepository.findAllByArticleUserId(userId)
    }
}
