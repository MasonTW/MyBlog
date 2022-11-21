package com.mx.blog.exception

class ArticleIsNotExistedException(override val message: String?) : RuntimeException(message)