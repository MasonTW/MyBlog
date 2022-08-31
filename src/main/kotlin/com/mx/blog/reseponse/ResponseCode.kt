package com.mx.blog.reseponse

import java.io.Serializable

enum class ResponseCode(val code: Int, val message: String): Serializable {
    SUCCESS(200, "成功"),
    UNAUTHORIZED(401,"未授权"),
    FORBIDDEN(403,"禁止访问"),
    NOT_FOUND(404,"无法找到"),
    INTERNAL_ERROR(500,"服务器内部错误"),
}