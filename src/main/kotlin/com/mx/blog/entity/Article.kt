package com.mx.blog.entity

import javax.persistence.*

@Entity
class Article(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var articleId: Long = -1,
    var articleTitle: String,
    var articleAddTime: String,
    var articleUpdateTime: String,
    var articleContent: String,
    var articleStar: Int = 0,
    var articleLookTimes: Int = 0,
    var articleCollectionNum: Int = 0,
    var articleUserId: Long,
    @OneToOne(targetEntity = Agreement::class, cascade = [CascadeType.ALL])
    var agreement: Agreement?,
    @OneToMany(targetEntity = Comment::class, cascade = [CascadeType.ALL], mappedBy = "articleId")
    var comments: MutableList<Comment>,
)
