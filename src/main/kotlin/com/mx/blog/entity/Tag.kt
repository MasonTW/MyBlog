package com.mx.blog.entity

import javax.persistence.GeneratedValue
import javax.persistence.Id

class Tag(
    @Id @GeneratedValue var tag_id: Long,
    var tagName: String,
    var tagAddTime: String,
    var tagListId: Int,
    var articleId: Int,
)
