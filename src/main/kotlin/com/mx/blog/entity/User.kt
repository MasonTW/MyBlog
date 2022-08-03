package com.mx.blog.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class User(
    @Id @GeneratedValue var id: Long,
    var userName: String,
    var userPassword: String,
    var userRegisterTime: String
    )
