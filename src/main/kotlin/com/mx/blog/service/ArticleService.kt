package com.mx.blog.service

import com.mx.blog.DTO.ArticleBasicDTO
import com.mx.blog.DTO.ArticleInfoDTO
import com.mx.blog.entity.Article
import com.mx.blog.repository.ArticleRepository
import com.mx.blog.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository,
) {
    fun createArticle(articleBasicDTO: ArticleBasicDTO, userid: Long): ArticleInfoDTO {
        val newArticle = Article.toArticle(articleBasicDTO, userid)
        val saveResult = articleRepository.save(newArticle)
        return Article.toArticleInfoDTO(saveResult)
    }

    fun getArticlesByUserName(userName: String): List<ArticleInfoDTO> {
        val userId = userRepository.findByUserName(userName).id
        return articleRepository.findAllByArticleUserId(userId)
            .filter { !it.isDeleted  }
            .map { Article.toArticleInfoDTO(it) }
    }

    fun deleteArticle(articleId: Long): Boolean {
        return try {
            val articleEntity = articleRepository.findById(articleId).get()
            articleEntity.isDeleted = true
            articleRepository.saveAndFlush(articleEntity)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getRandomTenArticles(): List<ArticleInfoDTO> {
        return articleRepository.findRandomArticles()
            .filter { !it.isDeleted  }
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

}
