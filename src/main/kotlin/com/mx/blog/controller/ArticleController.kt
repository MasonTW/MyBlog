package com.mx.blog.controller

import com.mx.blog.DTO.ArticleBasicDTO
import com.mx.blog.DTO.ArticleInfoDTO
import com.mx.blog.reseponse.ResponseCode
import com.mx.blog.reseponse.ResponseResult
import com.mx.blog.service.ArticleService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class ArticleController(
    private val articleService: ArticleService,
){
    @PostMapping("/article")
    @ResponseBody
    fun createArticle(@RequestBody articleBasicDTO: ArticleBasicDTO, @RequestAttribute("userId") userId: Long): ArticleInfoDTO {
       return articleService.createArticle(articleBasicDTO, userId)
    }

    @PostMapping("/article/update")
    @ResponseBody
    fun updateArticle(@RequestBody articleUpdateDTO: ArticleBasicDTO, articleId: Long): ArticleInfoDTO {
        return articleService.updateArticle(articleUpdateDTO, articleId)
    }

    @PostMapping("/article/{articleId}")
    @ResponseBody
    fun deleteArticle(@PathVariable articleId: Long, @RequestAttribute("userId") userId: Long): Boolean {
        return articleService.deleteArticle(articleId)
    }

    @GetMapping("/article/{userName}")
    @ResponseBody
    fun getArticlesByUserName(@PathVariable userName: String): List<ArticleInfoDTO> {
       return articleService.getArticlesByUserName(userName)
    }

    @GetMapping("/article/random")
    @ResponseBody
    fun getRandomTenArticles(): ResponseResult {
        val articles = articleService.getRandomTenArticles()
        return ResponseResult(ResponseCode.SUCCESS, articles)
    }
}