package com.mx.blog.repository

import com.mx.blog.entity.Agreement
import com.mx.blog.entity.Article
import org.springframework.data.jpa.repository.JpaRepository

interface AgreementRepository: JpaRepository<Agreement, Long> {
    fun findByArticleId(articleId: Long): Agreement?
}
