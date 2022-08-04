package com.mx.blog.controller

import com.mx.blog.DTO.ArticleCreateDTO
import com.mx.blog.service.ArticleService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@Controller
class ArticleController(
    private val articleService: ArticleService
){
    @PostMapping("/article")
    @ResponseBody
    fun createArticle(@RequestBody articleCreateDTO: ArticleCreateDTO, session: HttpSession) {
        articleService.createArticle(articleCreateDTO, session)
    }
}