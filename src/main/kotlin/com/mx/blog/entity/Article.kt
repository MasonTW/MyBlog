package com.mx.blog.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Article(
    @Id @GeneratedValue() var articleId: Long = -1,
    var articleTitle: String,
    var articleAddTime: String,
    var articleUpdateTime: String,
    var articleContent: String,
    var articleStar: Int = 0,
    var articleLookTimes: Int = 0,
    var articleCollectionNum: Int = 0,
    var articleUserId: Long,
)
