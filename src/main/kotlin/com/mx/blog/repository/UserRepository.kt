package com.mx.blog.repository

import com.mx.blog.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findAllByUserName(userName: String): List<User>
    fun findByUserAccountAndUserPassword(userAccount: String, userPassword: String): User?
    fun findByIdAndDeleted(userId: Long, deleted: Boolean = false): User?
    fun findByUserAccount(userAccount: String): User?
}