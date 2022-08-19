package com.mx.blog.service

import com.mx.blog.DTO.ArticleBasicDTO
import com.mx.blog.DTO.ArticleCreateDTO
import com.mx.blog.entity.Article
import com.mx.blog.repository.ArticleRepository
import com.mx.blog.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository,
) {
    fun createArticle(articleGetDTO: ArticleCreateDTO, userid: Long): Article {
        val newArticle = Article(
            articleTitle = articleGetDTO.articleTitle,
            articleContent = articleGetDTO.articleContent,
            articleAddTime = System.currentTimeMillis().toString(),
            articleUpdateTime = System.currentTimeMillis().toString(),
            articleUserId = userid,
            comments = mutableListOf(),
            agreement = null
        )
        return articleRepository.save(newArticle)
    }

    fun getArticlesByUserName(userName: String): List<ArticleCreateDTO> {
        val userId = userRepository.findByUserName(userName).id
        return articleRepository.findAllByArticleUserId(userId).map { articleMapper(it) }
    }

    fun articleMapper(article: Article): ArticleCreateDTO {
        val agreement = article.agreement
        return ArticleCreateDTO(
            articleTitle = article.articleTitle,
            articleContent = article.articleContent,
            articleStar = article.articleStar,
            articleCollectionNum = article.articleCollectionNum,
            articleLookTimes = article.articleLookTimes,
            commentsNum = article.comments.size.toLong(),
            agreementNum = agreement?.agreementNum ?: 0
        )
    }

    fun deleteArticle(articleId: Long) {
        articleRepository.deleteById(articleId)
    }

    fun getRandomTenArticles(): List<ArticleCreateDTO> {
        return articleRepository.findRandomArticles().map { articleMapper(it) }
    }

    fun updateArticle(articleUpdateDTO: ArticleBasicDTO, articleId: Long) {
        val article = articleRepository.findById(articleId).get()
        article.articleTitle = articleUpdateDTO.articleTitle
        article.articleContent = articleUpdateDTO.articleContent
        article.articleUpdateTime = System.currentTimeMillis().toString()
        articleRepository.save(article)
    }

}
