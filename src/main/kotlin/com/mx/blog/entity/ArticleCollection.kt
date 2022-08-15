package com.mx.blog.entity

import javax.persistence.*

@Entity
class ArticleCollection(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long = -1,
    var articleId: Long = -1,
    var collectionId: Long
)
