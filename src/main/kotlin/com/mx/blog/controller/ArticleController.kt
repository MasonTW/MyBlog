package com.mx.blog.controller

import com.mx.blog.DTO.ArticleBasicDTO
import com.mx.blog.DTO.ArticleCreateDTO
import com.mx.blog.service.ArticleService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@Controller
class ArticleController(
    private val articleService: ArticleService,
){
    @PostMapping("/article")
    @ResponseBody
    fun createArticle(@RequestBody articleCreateDTO: ArticleCreateDTO,@RequestAttribute("userId") userId: Long) {
        articleService.createArticle(articleCreateDTO, userId)
    }

    @PostMapping("/article/update")
    @ResponseBody
    fun updateArticle(@RequestBody articleUpdateDTO: ArticleBasicDTO, articleId: Long) {
        articleService.updateArticle(articleUpdateDTO, articleId)
    }

    @PostMapping("/article/{articleId}")
    @ResponseBody
//    @PreAuthorize("hasRole('ADMIN')")
    fun deleteArticle(@PathVariable articleId: Long, session: HttpSession) {
//        val authentication = SecurityContextHolder.getContext().authentication
        articleService.deleteArticle(articleId)
    }

    @GetMapping("/article/{userName}")
    @ResponseBody
    fun getArticlesByUserName(@PathVariable userName: String): List<ArticleCreateDTO> {
       return articleService.getArticlesByUserName(userName)
    }

    @GetMapping("/article/random")
    @ResponseBody
    fun getRandomTenArticles(): List<ArticleCreateDTO> {
        return articleService.getRandomTenArticles()
    }
}