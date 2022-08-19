package com.mx.blog.config

import com.mx.blog.interceptors.JWTInterceptors
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class InterceptorsConfig: WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(JWTInterceptors())
            .addPathPatterns("/**")
            .excludePathPatterns("/login")
            .excludePathPatterns("/article/random")
            .excludePathPatterns("/register")
    }



}