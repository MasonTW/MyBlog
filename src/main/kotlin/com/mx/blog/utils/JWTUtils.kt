package com.mx.blog.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.mx.blog.DTO.User.UserDTO
import java.util.*

class JWTUtils {
    companion object {
        private const val sign = "Mason"

        //生成token
        fun getToken(user: UserDTO): String {
            //设置过期时间（七天）
            val instance = Calendar.getInstance()
            instance.add(Calendar.DATE, 7)
            return JWT.create()
                .withClaim("userId", user.userId)//payload
                .withClaim("userName", user.userName)
                .withExpiresAt(instance.time)//过期时间
                .sign(Algorithm.HMAC256(sign))//签名
        }

        //验证token
        fun verify(token: String): DecodedJWT {
            if (token.isEmpty()) {
                throw Exception()
            }
            val verification = JWT.require(Algorithm.HMAC256(sign)).build()
            return verification.verify(token)
        }


    }

}