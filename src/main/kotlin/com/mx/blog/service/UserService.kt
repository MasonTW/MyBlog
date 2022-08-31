package com.mx.blog.service

import com.mx.blog.DTO.UserDTO
import com.mx.blog.DTO.UserLoginDTO
import com.mx.blog.DTO.UserRegisterDTO
import com.mx.blog.entity.User
import com.mx.blog.repository.UserRepository
import com.mx.blog.utils.JWTUtils
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun findUserById(id: Long): UserDTO {
        val user = userRepository.findById(id).get()
        return User.toUserDTO(user)
    }

    fun createUser(userRegisterDTO: UserRegisterDTO): User {
        val newUser = User.toUser(userRegisterDTO)
        return userRepository.save(newUser)
    }

    fun login(userLoginDTO: UserLoginDTO): Boolean {
        val findResult = userRepository.findByUserAccountAndUserPassword(
            userAccount = userLoginDTO.userAccount,
            userPassword = userLoginDTO.userPassword
        )
        return if (findResult != null){
            val user = UserDTO(userId = findResult.id, userName = findResult.userName)
            val token = JWTUtils.getToken(user = user)
            println(token)
            return true
        } else false
    }

    fun deleteUser(userId: Long): Boolean {
        //todo verify permission
        val user = userRepository.findById(userId).get()
        return if (user == null || user.isDeleted) {
            false
        }else {
            user.isDeleted = true
            userRepository.saveAndFlush(user)
            true
        }
    }
}