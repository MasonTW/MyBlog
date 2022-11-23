package com.mx.blog.DTO.User

import javax.validation.constraints.Size


data class UserRegisterDTO(
    val userName: String,
    @get:Size(max = 10)
    val userAccount: String,
    val userPassword: String,
)