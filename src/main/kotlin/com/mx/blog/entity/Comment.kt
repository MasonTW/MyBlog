package com.mx.blog.entity

import javax.persistence.*

@Entity
class Comment(
    @Id @GeneratedValue var commentId: Long = -1,
    var articleId: Long,
    var commentTime: String,
    var userId: Long,
    var commentStar: Int = 0,
    var commentContent: String,
)
