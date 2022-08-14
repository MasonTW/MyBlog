package com.mx.blog.DTO

import javax.persistence.GeneratedValue
import javax.persistence.Id


data class CommentDTO(
    var userId: Long,
    var commentStar: Int = 0,
    var commentContent: String,
)
