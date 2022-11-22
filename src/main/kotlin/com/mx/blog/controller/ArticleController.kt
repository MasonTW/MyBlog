package com.mx.blog.controller

import com.mx.blog.DTO.article.ArticleBasicDTO
import com.mx.blog.DTO.article.ArticleInfoDTO
import com.mx.blog.reseponse.ResponseCode
import com.mx.blog.reseponse.ResponseResult
import com.mx.blog.service.ArticleService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class ArticleController(
    private val articleService: ArticleService,
){
    @PostMapping("/articles")
    @ResponseBody
    fun createArticle(@RequestBody articleBasicDTO: ArticleBasicDTO, @RequestAttribute("userId") userId: Long): ArticleInfoDTO {
        return articleService.createArticle(articleBasicDTO, userId)
    }

    @PutMapping("/articles/{articleId}")
    @ResponseBody
    fun updateArticle(@RequestBody articleUpdateDTO: ArticleBasicDTO, @PathVariable articleId: Long): ArticleInfoDTO {
        return articleService.updateArticle(articleUpdateDTO, articleId)
    }

    @DeleteMapping("/articles/{articleId}")
    @ResponseBody
    fun deleteArticle(@PathVariable articleId: Long, @RequestAttribute("userId") userId: Long): Boolean {
        return articleService.deleteArticle(articleId)
    }

    @GetMapping("/articles/{articleId}")
    @ResponseBody
    fun getArticle(@PathVariable articleId: Long, @RequestAttribute("userId") userId: Long): ArticleInfoDTO {
        return articleService.getArticlesById(articleId, userId)
    }

    @GetMapping("/articles")
    @ResponseBody
    fun getArticlesByUserName(@RequestParam("userName") userName: String): List<ArticleInfoDTO> {
       return articleService.getArticlesByUserName(userName)
    }

    @GetMapping("/articles/random-articles")
    @ResponseBody
    fun getRandomTenArticles(): ResponseResult {
        val articles = articleService.getRandomTenArticles()
        return ResponseResult(ResponseCode.SUCCESS, articles)
    }
}