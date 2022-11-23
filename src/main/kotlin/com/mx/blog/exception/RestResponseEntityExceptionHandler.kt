package com.mx.blog.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class RestResponseEntityExceptionHandler {

    @ExceptionHandler(ArticleIsNotExistedException::class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun handleArticleIsNotExistedException(){

    }

    @ExceptionHandler(DuplicatedRegisterException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun handleDuplicatedRegisterException(){

    }
}