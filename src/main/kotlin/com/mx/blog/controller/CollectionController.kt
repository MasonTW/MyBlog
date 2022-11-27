package com.mx.blog.controller

import com.mx.blog.DTO.article.ArticleInfoDTO
import com.mx.blog.DTO.conllection.CollectionCreateDTO
import com.mx.blog.DTO.conllection.CollectionsDTO
import com.mx.blog.entity.Collection
import com.mx.blog.service.CollectionService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class CollectionController(
    private val collectionService: CollectionService,
) {
    @GetMapping("/users/collections")
    @ResponseBody
    fun getCollection(@RequestAttribute("userId") userId: Long): List<CollectionsDTO> {
        return collectionService.getCollections(userId)
    }

    @PostMapping("/users/collections")
    @ResponseBody
    fun createCollection (@RequestBody collectionCreateDTO: CollectionCreateDTO): Collection {
       return collectionService.createCollection(collectionCreateDTO)
    }

    @PostMapping("/users/collections/articles/{articleId}")
    @ResponseBody
    fun addArticleToCollection(@PathVariable articleId: Long,@RequestParam collectionId: Long): Boolean {
        return collectionService.addArticle(articleId, collectionId)
    }

    @GetMapping("/users/collections/{collectionId}/articles")
    @ResponseBody
    fun getCollectionArticles(@PathVariable collectionId: Long): List<ArticleInfoDTO> {
        return collectionService.getArticles(collectionId)
    }
}