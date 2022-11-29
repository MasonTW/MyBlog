package com.mx.blog.repository

import com.mx.blog.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository: JpaRepository<Comment, Long> {
    fun findAllByArticleId(articleId: Long): List<Comment>
}