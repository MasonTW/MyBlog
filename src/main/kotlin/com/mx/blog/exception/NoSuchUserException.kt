package com.mx.blog.exception

class NoSuchUserException(override val message: String?) : RuntimeException(message)