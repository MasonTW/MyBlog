package com.mx.blog.repository

import com.mx.blog.entity.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ArticleRepository: JpaRepository<Article, Long> {
  fun findAllByArticleUserId(userId: Long): List<Article>

  @Query(value = "select * from article order by rand() limit 10", nativeQuery = true)
  fun findRandomArticles(): List<Article>

}
