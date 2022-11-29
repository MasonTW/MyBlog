package com.mx.blog.controller

import com.mx.blog.DTO.AgreementRecord
import com.mx.blog.entity.Agreement
import com.mx.blog.service.AgreementService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class AgreementController(
    private val agreementService: AgreementService,
){

    @PostMapping("/articles/{articleId}/agreements")
    @ResponseBody
    fun agreeArticle(@PathVariable articleId: Long, @RequestAttribute("userId") userId: Long): Agreement {
        return agreementService.agreeArticle(articleId, userId)
    }

    @DeleteMapping("/articles/{articleId}/agreements")
    @ResponseBody
    fun cancelArticleAgreement(@PathVariable articleId: Long, @RequestAttribute("userId") userId: Long): Boolean {
        return agreementService.cancelArticleAgreement(articleId, userId)
    }

    @GetMapping("/articles/{articleId}/agreements")
    @ResponseBody
    fun isArticleAgreed(@PathVariable articleId: Long, @RequestAttribute("userId") userId: Long): Boolean {
        return agreementService.checkAgreement(articleId, userId)
    }

    @GetMapping("/users/agreements")
    @ResponseBody
    fun userAgreementRecords(@RequestAttribute("userId") userId: Long): List<AgreementRecord> {
        return agreementService.getAgreementRecords(userId)
    }
}