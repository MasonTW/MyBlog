package com.mx.blog.service

import com.mx.blog.DTO.UserDTO
import com.mx.blog.DTO.UserLoginDTO
import com.mx.blog.DTO.UserRegisterDTO
import com.mx.blog.entity.User
import com.mx.blog.repository.UserRepository
import com.mx.blog.utils.JWTUtils
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.servlet.http.HttpSession

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun findUserById(id: Long): UserDTO {
        val user = userRepository.findById(id).get()
        return UserDTO(
            userId = user.id,
            userName = user.userName
        )
    }

    fun createUser(userRegisterDTO: UserRegisterDTO): User {
        val newUser = User(
            userName = userRegisterDTO.userName,
            userPassword = userRegisterDTO.userPassword,
            userRegisterTime = LocalDateTime.now().toString(),
            userAccount = userRegisterDTO.userAccount,
        )
        return userRepository.save(newUser)
    }

    fun login(userLoginDTO: UserLoginDTO, session: HttpSession): Boolean {
        val findResult = userRepository.findByUserAccountAndUserPassword(
            userAccount = userLoginDTO.userAccount,
            userPassword = userLoginDTO.userPassword
        )
        return if (findResult != null){
            val user = UserDTO(userId = findResult.id, userName = findResult.userName)
            val token = JWTUtils.getToken(user = user)
            session.setAttribute("userId",findResult.id)
            println(token)
            return true
        } else false
    }
}