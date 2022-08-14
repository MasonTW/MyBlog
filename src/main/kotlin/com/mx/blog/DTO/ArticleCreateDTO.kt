package com.mx.blog.DTO


data class ArticleCreateDTO(
    var articleTitle: String,
    var articleContent: String,
    var articleStar: Int = 0,
    var articleLookTimes: Int = 0,
    var articleCollectionNum: Int = 0,
    val commentsNum: Long,
    val agreementNum: Long,
)
