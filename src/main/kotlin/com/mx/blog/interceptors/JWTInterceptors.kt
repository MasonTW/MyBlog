package com.mx.blog.interceptors

import com.auth0.jwt.exceptions.TokenExpiredException
import com.fasterxml.jackson.databind.ObjectMapper
import com.mx.blog.utils.JWTUtils
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTInterceptors : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val map = mutableMapOf<String, Any>()
        return try {
            val token = request.getHeader("token")//获取请求头令牌
            checkToken(token, request)
            true
        } catch (e: Exception){
            if (e.javaClass == TokenExpiredException::class.java) {
                map["msg"] = "token过期"
            }
            e.printStackTrace()
            map["msg"] = "token异常"
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            val json = ObjectMapper().writeValueAsString(map)
            response.contentType = "application/json;charset=UTF-8"
            response.writer.println(json)
            false
        }
    }

    private fun checkToken(token: String, request: HttpServletRequest): Boolean {
        val verify = JWTUtils.verify(token)
        val userId = verify.getClaim("userId").asLong()
        request.setAttribute("userId", userId)
        return true
    }
}