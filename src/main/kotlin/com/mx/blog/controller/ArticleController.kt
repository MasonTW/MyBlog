package com.mx.blog.controller

import com.mx.blog.DTO.ArticleCreateDTO
import com.mx.blog.DTO.ArticleGetDTO
import com.mx.blog.entity.Agreement
import com.mx.blog.entity.Comment
import com.mx.blog.service.AgreementService
import com.mx.blog.service.ArticleService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@Controller
class ArticleController(
    private val articleService: ArticleService,
    private val agreementService: AgreementService,
){
    @PostMapping("/article")
    @ResponseBody
    fun createArticle(@RequestBody articleCreateDTO: ArticleCreateDTO, session: HttpSession) {
        articleService.createArticle(articleCreateDTO, session)
    }

    @GetMapping("/article/{userName}")
    @ResponseBody
    fun getArticlesByUserName(@PathVariable userName: String): List<ArticleGetDTO> {
       return articleService.getArticlesByUserName(userName)
    }

    @PostMapping("/article/{articleId}/agreement")
    @ResponseBody
    fun agreeArticle(@PathVariable articleId: Long, session: HttpSession): Agreement {
        val userId = session.getAttribute("userId") as Long
        return agreementService.agreeArticle(articleId, userId)
    }
}