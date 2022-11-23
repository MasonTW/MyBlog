package com.mx.blog.service

import com.mx.blog.DTO.article.ArticleInfoDTO
import com.mx.blog.DTO.conllection.CollectionCreateDTO
import com.mx.blog.DTO.conllection.CollectionsDTO
import com.mx.blog.entity.Article
import com.mx.blog.entity.ArticleCollection
import com.mx.blog.entity.Collection
import com.mx.blog.repository.ArticleCollectionRepository
import com.mx.blog.repository.ArticleRepository
import com.mx.blog.repository.CollectionRepository
import org.springframework.stereotype.Service

@Service
class CollectionService(
    private val collectionRepository: CollectionRepository,
    private val articleCollectionRepository: ArticleCollectionRepository,
    private val articleRepository: ArticleRepository,
) {
    fun getCollections(userId: Long): List<CollectionsDTO> {
        return collectionRepository.findAllByUserId(userId).map { Collection.toCollectionsDTO(it) }
    }

    fun createCollection(collectionCreateDTO: CollectionCreateDTO): Collection {
        val collection = Collection.toCollection(collectionCreateDTO)
        return collectionRepository.save(collection)
    }

    fun addArticle(articleId: Long, collectionId: Long): Boolean {
        val articleCollection = ArticleCollection(articleId = articleId, collectionId = collectionId)
        val collection = collectionRepository.findById(collectionId).get()
        collection.articleNum++
        return try {
            articleCollectionRepository.save(articleCollection)
            collectionRepository.save(collection)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getArticles(collectionId: Long): List<ArticleInfoDTO> {
        val articleCollections = articleCollectionRepository.findAllByCollectionId(collectionId)
        return if (articleCollections.isEmpty()) {
            emptyList()
        } else {
            articleCollections
                .map { articleRepository.findById(it.articleId) }
                .filter { it.isPresent }
                .map { Article.toArticleInfoDTO(it.get()) }
        }

    }


}