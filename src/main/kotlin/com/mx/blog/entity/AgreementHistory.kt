package com.mx.blog.entity

import javax.persistence.*

@Entity
@Table(indexes = [Index(name = "index_tb_agreementUserId", columnList = "agreementUserId")])
class AgreementHistory(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long = -1,
    val articleId: Long,
    var agreementUserId: Long,
    var agreementTime: String,
    var isDeleted: Boolean = false,
)
