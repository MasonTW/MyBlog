package com.mx.blog.repository

import com.mx.blog.entity.Collection
import com.mx.blog.entity.Comment
import com.mx.blog.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CollectionRepository: JpaRepository<Collection, Long> {
    fun findAllByUserId(userId: Long): List<Collection>
}