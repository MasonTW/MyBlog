package com.mx.blog.entity

import javax.persistence.*

@Entity
class Collection(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var collectionId: Long = -1,
    var userId: Long,
    var name: String,
    var articleNum: Long = 0,
    @OneToMany(targetEntity = ArticleCollection::class, cascade = [CascadeType.ALL], mappedBy = "collectionId")
    var collectionArticles: List<ArticleCollection> = mutableListOf(),
)
