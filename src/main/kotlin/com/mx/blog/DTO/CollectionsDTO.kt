package com.mx.blog.DTO

import com.mx.blog.entity.ArticleCollection
import javax.persistence.CascadeType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

data class CollectionsDTO(
    val collectionId: Long,
    val name: String,
    val articleNum: Long,
)