package com.mx.blog.controller

import com.mx.blog.DTO.UserDTO
import com.mx.blog.DTO.UserRegisterDTO
import com.mx.blog.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpSession

@Controller
class UserController(
    private val userService: UserService
){

    @GetMapping("/user/{id}")
    @ResponseBody
    fun getUser(@PathVariable id: String): UserDTO{
        val userId = id.toLong()
        return userService.findUserById(userId)
    }

    @PostMapping("/login")
    @ResponseBody
    fun login(@RequestBody userRegisterDTO: UserRegisterDTO, session : HttpSession): Boolean{
       return userService.login(userRegisterDTO, session)
    }

    @PostMapping("/user")
    @ResponseBody
    fun createUser(@RequestBody userRegisterDTO: UserRegisterDTO): UserDTO{
        val createUser = userService.createUser(userRegisterDTO)
        return UserDTO(
            userId = createUser.id,
            userName = createUser.userName
        )
    }
}