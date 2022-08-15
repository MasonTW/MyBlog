package com.mx.blog.entity

import javax.persistence.*

@Entity
@Table(indexes = [Index(name = "index_tb_agreementUserId", columnList = "agreementUserId")])
class AgreementHistory(
    @Id @GeneratedValue var id: Long = -1,
    var articleId: Long,
    var agreementUserId: Long,
    var agreementTime: String,
    var isDeleted: Boolean = false,
)
