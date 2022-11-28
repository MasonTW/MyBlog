package com.mx.blog

import com.mx.blog.entity.Article
import com.mx.blog.entity.User
import kotlin.random.Random

object RandomData {
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun randomString(length: Int = 10): String {
       return (1..length)
           .map { Random.nextInt(0, charPool.size) }
           .map (charPool::get)
           .joinToString("")
    }

    fun generateArticle(userId: Long = Random.nextLong()): Article {
        return Article(
            articleTitle = randomString(),
            articleContent = randomString(),
            articleUpdateTime = randomString(),
            articleAddTime =randomString(),
            articleUserId = userId,
            agreement = null,
            comments = mutableListOf()
        )
    }

    fun generateUser(userId: Long = Random.nextLong()): User {
        return User(
            id = userId,
            userAccount = randomString(),
            userPassword = randomString(),
            userName = randomString(),
            userRegisterTime = randomString()
        )
    }
}