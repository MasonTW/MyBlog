package com.mx.blog.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class User(
    @Id @GeneratedValue var id: Long = -1,
    var userAccount: String,
    var userName: String,
    var userPassword: String,
    var userRegisterTime: String
    )
