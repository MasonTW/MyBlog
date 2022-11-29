package com.mx.blog.service

import com.mx.blog.DTO.article.ArticleBasicDTO
import com.mx.blog.DTO.article.ArticleInfoDTO
import com.mx.blog.entity.Article
import com.mx.blog.exception.ArticleIsNotExistedException
import com.mx.blog.repository.ArticleRepository
import com.mx.blog.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository,
    private val agreementService: AgreementService,
) {
    fun createArticle(articleBasicDTO: ArticleBasicDTO, userid: Long): ArticleInfoDTO {
        val newArticle = Article.toArticle(articleBasicDTO, userid)
        val saveResult = articleRepository.save(newArticle)
        return Article.toArticleInfoDTO(article = saveResult, isAuthor = true, isAgreed = false)
    }

    fun getArticlesByUserName(userName: String): List<ArticleInfoDTO> {
        val users = userRepository.findAllByUserName(userName)
        return users.map { user ->
            articleRepository.findAllByArticleUserId(user.id)
                .filter { !it.deleted }
                .map { Article.toArticleInfoDTO(it) }
        }.flatten().toList()
    }

    fun deleteArticle(articleId: Long): Boolean {
        try {
            val articleEntity = articleRepository.findById(articleId).get()
            articleEntity.deleted = true
            articleRepository.saveAndFlush(articleEntity)
            return true
        }catch (e: Exception) {
            throw ArticleIsNotExistedException("article not found")
        }
    }

    fun getRandomTenArticles(): List<ArticleInfoDTO> {
        return articleRepository.findRandomArticles()
            .map { Article.toArticleInfoDTO(it) }
    }

    fun updateArticle(articleUpdateDTO: ArticleBasicDTO, articleId: Long): ArticleInfoDTO {
        val article = articleRepository.findById(articleId).get()
        article.articleTitle = articleUpdateDTO.articleTitle
        article.articleContent = articleUpdateDTO.articleContent
        article.articleUpdateTime = System.currentTimeMillis().toString()
        val saveResult = articleRepository.save(article)
        return Article.toArticleInfoDTO(saveResult)
    }

    fun getArticlesById(articleId: Long, userId: Long? = null): ArticleInfoDTO {
        val article = articleRepository.findById(articleId).orElseThrow {
            throw ArticleIsNotExistedException("Article not found")
        }
        if (article.deleted) {
            throw ArticleIsNotExistedException("Article has been deleted")
        }

        val isAuthor = checkArticleRelationship(articleId, userId)
        val isAgreed = checkAgreement(articleId, userId)
        return Article.toArticleInfoDTO(article, isAuthor, isAgreed)
    }

    private fun checkAgreement(articleId: Long, userId: Long?): Boolean {
        return userId?.let {
            agreementService.checkAgreement(articleId, userId)
        } ?: false
    }

    private fun checkArticleRelationship(articleId: Long, userId: Long?) = userId == articleId
}
