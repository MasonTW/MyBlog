package com.mx.blog.repository

import com.mx.blog.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByUserName(userName: String): User
    fun findByUserAccountAndUserPassword(userAccount: String, userPassword: String ): User?
    fun findByIdAndIsDeleted(userId: Long, isDeleted: Boolean): User?
}