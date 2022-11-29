package com.mx.blog.repository

import com.mx.blog.entity.Collection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CollectionRepository: JpaRepository<Collection, Long> {
    fun findAllByUserId(userId: Long): List<Collection>
}