package com.mx.blog.DTO

import com.mx.blog.entity.Agreement
import com.mx.blog.entity.Comment


data class ArticleGetDTO(
    var articleTitle: String,
    var articleContent: String,
    var articleStar: Int = 0,
    var articleLookTimes: Int = 0,
    var articleCollectionNum: Int = 0,
    val comments: MutableList<Comment>,
    val agreementNum: Long,
)
