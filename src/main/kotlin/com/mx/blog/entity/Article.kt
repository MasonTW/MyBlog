package com.mx.blog.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Article(
    @Id @GeneratedValue var articleId: Long,
    var articleTitle: String,
    var articleAddTime: String,
    var articleContent: String,
    var articleStar: Int = 0,
    var articleLookTimes: Int = 0,
    var articleCollectionNum: Int = 0,
)
