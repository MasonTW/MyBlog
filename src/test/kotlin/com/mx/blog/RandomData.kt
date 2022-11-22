package com.mx.blog

import kotlin.random.Random

object RandomData {
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun randomString(length: Int = 10): String {
       return (1..length)
           .map { Random.nextInt(0, charPool.size) }
           .map (charPool::get)
           .joinToString("")
    }
}