package com.mx.blog.DTO.conllection

data class CollectionCreateDTO(
    var userId: Long,
    val name: String,
    val articleNum: Long = 0,
)