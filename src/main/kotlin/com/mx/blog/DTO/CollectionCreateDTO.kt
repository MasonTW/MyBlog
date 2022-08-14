package com.mx.blog.DTO

data class CollectionCreateDTO(
    var userId: Long,
    val name: String,
    val articleNum: Long = 0,
)
