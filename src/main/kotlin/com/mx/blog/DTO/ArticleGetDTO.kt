package com.mx.blog.DTO


data class ArticleGetDTO(
    var articleTitle: String,
    var articleContent: String,
    var articleStar: Int = 0,
    var articleLookTimes: Int = 0,
    var articleCollectionNum: Int = 0,
)
