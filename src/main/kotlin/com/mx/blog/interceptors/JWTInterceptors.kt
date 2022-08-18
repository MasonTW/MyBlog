package com.mx.blog.interceptors

import com.auth0.jwt.exceptions.AlgorithmMismatchException
import com.auth0.jwt.exceptions.SignatureVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.fasterxml.jackson.databind.ObjectMapper
import com.mx.blog.utils.JWTUtils
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTInterceptors : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val map = mutableMapOf<String, Any>()
        val token = request.getHeader("token")//获取请求头令牌
        try {
            JWTUtils.verify(token)
            return true
        } catch (e: SignatureVerificationException) {
            e.printStackTrace()
            map["msg"] = "无效签名"
            println("无效签名")
        } catch (e: TokenExpiredException) {
            e.printStackTrace()
            map["msg"] = "token过期"
        } catch (e: AlgorithmMismatchException) {
            e.printStackTrace()
            map["msg"] = "算法不一致"
        } catch (e: Exception) {
            e.printStackTrace()
            map["msg"] = "token无效"
        }
        val json = ObjectMapper().writeValueAsString(map)
        response.contentType = "application/json;charset=UTF-8"
        response.writer.println(json)
        return false
    }
}