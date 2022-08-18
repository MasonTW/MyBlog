package com.mx.blog.controller

import com.mx.blog.entity.Agreement
import com.mx.blog.service.AgreementService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@Controller
class AgreementController(
    private val agreementService: AgreementService,
){

    @PostMapping("/article/{articleId}/agreement")
    @ResponseBody
    fun agreeArticle(@PathVariable articleId: Long, session: HttpSession): Agreement {
        val userId = session.getAttribute("userId") as Long
        return agreementService.agreeArticle(articleId, userId)
    }

    @PostMapping("/article/{articleId}/agreement/cancel")
    @ResponseBody
    fun cancelArticleAgreement(@PathVariable articleId: Long, session: HttpSession): Boolean {
        val userId = session.getAttribute("userId") as Long
        return agreementService.cancelArticleAgreement(articleId, userId)
    }

    @GetMapping("/article/{articleId}/agreement")
    @ResponseBody
    fun isArticleAgreed(@PathVariable articleId: Long, session: HttpSession): Boolean {
        val userId = session.getAttribute("userId") as Long
        return agreementService.isAgreed(articleId, userId)
    }
}