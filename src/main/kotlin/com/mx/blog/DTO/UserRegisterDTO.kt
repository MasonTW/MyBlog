package com.mx.blog.DTO

import javax.persistence.CascadeType
import javax.persistence.OneToMany

data class UserRegisterDTO(
    val userName: String,
    val userAccount: String,
    val userPassword: String,
)
