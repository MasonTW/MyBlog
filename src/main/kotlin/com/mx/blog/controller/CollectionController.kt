package com.mx.blog.controller

import com.mx.blog.DTO.ArticleInfoDTO
import com.mx.blog.DTO.CollectionCreateDTO
import com.mx.blog.DTO.CollectionsDTO
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
    fun getCollection(@RequestParam userId: Long): List<CollectionsDTO> {
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

    @GetMapping("/users/collections/{collectionId}")
    @ResponseBody
    fun getCollectionArticles(@PathVariable collectionId: Long): List<ArticleInfoDTO> {
        return collectionService.getArticles(collectionId)
    }
}