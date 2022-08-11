package com.mx.blog.service

import com.mx.blog.DTO.ArticleCreateDTO
import com.mx.blog.DTO.ArticleGetDTO
import com.mx.blog.entity.Agreement
import com.mx.blog.entity.Article
import com.mx.blog.repository.ArticleRepository
import com.mx.blog.repository.UserRepository
import org.springframework.stereotype.Service
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
            comments = mutableListOf(),
            agreement = null
        )
        return articleRepository.save(newArticle)
    }

    fun getArticlesByUserName(userName: String): List<ArticleGetDTO> {
        val userId = userRepository.findByUserName(userName).id
        return articleRepository.findAllByArticleUserId(userId).map { articleMapper(it) }
    }

    private fun articleMapper(article: Article): ArticleGetDTO {
        val agreement = article.agreement
        return ArticleGetDTO(
            articleTitle = article.articleTitle,
            articleContent = article.articleContent,
            articleStar = article.articleStar,
            articleCollectionNum = article.articleCollectionNum,
            articleLookTimes = article.articleLookTimes,
            comments = article.comments,
            agreementNum = agreement?.agreementNum ?: 0
        )
    }

}
