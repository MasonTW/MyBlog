package com.mx.blog.DTO


data class CommentDTO(
    var userId: Long,
    var commentStar: Int = 0,
    var commentContent: String,
)
