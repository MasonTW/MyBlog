package com.mx.blog.repository

import com.mx.blog.entity.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository: JpaRepository<Article, Long> {
  fun findAllByArticleUserId(userId: Long): List<Article>

}
