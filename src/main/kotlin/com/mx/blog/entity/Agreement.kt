package com.mx.blog.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Agreement(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var agreementId: Long = -1,
    var articleId: Long,
    var agreementNum: Long,
)
