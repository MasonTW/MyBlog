package com.mx.blog.reseponse

class ResponseResult (responseCode: ResponseCode, data: Any) {
    val code: Int = responseCode.code
    val message: String = responseCode.message
    val data: Any = data
}