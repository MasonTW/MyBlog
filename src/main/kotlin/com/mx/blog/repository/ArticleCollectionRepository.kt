package com.mx.blog.repository

import com.mx.blog.entity.ArticleCollection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleCollectionRepository: JpaRepository<ArticleCollection, Long> {
    fun findAllByCollectionId(collectionId: Long): List<ArticleCollection>

}