package com.mx.blog.controller

import com.mx.blog.DTO.User.UserDTO
import com.mx.blog.DTO.User.UserLoginDTO
import com.mx.blog.DTO.User.UserRegisterDTO
import com.mx.blog.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class UserController(
    private val userService: UserService
){

    @GetMapping("/users/{id}")
    @ResponseBody
    fun getUser(@PathVariable id: String): UserDTO {
        val userId = id.toLong()
        return userService.findUserById(userId)

    }

    @PostMapping("/login")
    @ResponseBody
    fun login(@RequestBody userLoginDTO: UserLoginDTO): Boolean{
       return userService.login(userLoginDTO)
    }

    @PostMapping("/register")
    @ResponseBody
    fun createUser(@RequestBody userRegisterDTO: UserRegisterDTO): UserDTO {
        val createUser = userService.createUser(userRegisterDTO)
        return UserDTO(
            userId = createUser.id,
            userName = createUser.userName
        )
    }

}