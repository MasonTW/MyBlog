package com.mx.blog.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class AgreementHistory(
    @Id @GeneratedValue var id: Long = -1,
    var articleId: Long,
    var agreementUserId: Long,
    var agreementTime: String,
    var isDeleted: Boolean = false,
)
