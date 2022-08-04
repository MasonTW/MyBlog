package com.mx.blog.entity

import javax.persistence.GeneratedValue
import javax.persistence.Id

class Comment(
    @Id @GeneratedValue var commentId: Long,
    var articleId: String,
    var commentTime: String,
    var userId: Int,
    var commentStar: Int = 0,
    var commentContent: String,
)
