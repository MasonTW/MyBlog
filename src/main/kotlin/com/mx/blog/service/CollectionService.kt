package com.mx.blog.service

import com.mx.blog.DTO.ArticleCreateDTO
import com.mx.blog.DTO.CollectionCreateDTO
import com.mx.blog.DTO.CollectionsDTO
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
    private val articleService: ArticleService,
) {
    fun getCollections(userId: Long): List<CollectionsDTO> {
        return collectionRepository.findAllByUserId(userId).map { collectionMapper(it) }
    }

    fun createCollection(collectionCreateDTO: CollectionCreateDTO): Collection {
        val collection = collectionMapper(collectionCreateDTO)
        return collectionRepository.save(collection)
    }

    fun addArticle(articleId: Long, collectionId: Long) {
        val articleCollection = ArticleCollection(articleId = articleId, collectionId = collectionId)
        var collection = collectionRepository.findById(collectionId).get()
        collection.articleNum++
        articleCollectionRepository.save(articleCollection)
        collectionRepository.save(collection)
    }

    fun getArticles(collectionId: Long): List<ArticleCreateDTO> {
        val articleCollections = articleCollectionRepository.findAllByCollectionId(collectionId)
        return if (articleCollections.isEmpty()) {
            emptyList()
        } else {
            articleCollections.map { articleRepository.findById(it.articleId) }.filter { it.isPresent }
                .map { articleService.articleMapper(it.get()) }
        }

    }

    private fun collectionMapper(collectionCreateDTO: CollectionCreateDTO): Collection {
        return Collection(userId = collectionCreateDTO.userId, name = collectionCreateDTO.name)
    }

    private fun collectionMapper(collection: Collection): CollectionsDTO {
        return CollectionsDTO(
            collectionId = collection.collectionId,
            name = collection.name,
            articleNum = collection.collectionArticles.size.toLong()
        )
    }
}