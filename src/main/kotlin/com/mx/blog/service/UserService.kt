package com.mx.blog.service

import com.mx.blog.DTO.User.UserDTO
import com.mx.blog.DTO.User.UserLoginDTO
import com.mx.blog.DTO.User.UserRegisterDTO
import com.mx.blog.entity.User
import com.mx.blog.exception.DuplicatedRegisterException
import com.mx.blog.repository.UserRepository
import com.mx.blog.utils.JWTUtils
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun findUserById(id: Long): UserDTO {
        val user = userRepository.findById(id).get()
        return User.toUserDTO(user)
    }

    fun createUser(userRegisterDTO: UserRegisterDTO): UserDTO {
        checkAccount(userRegisterDTO.userAccount)
        val newUser = User.toUser(userRegisterDTO)
        userRepository.save(newUser).let {
            return UserDTO(
                userId = it.id,
                userName = it.userName
            )
        }
    }

    private fun checkAccount(userAccount: String) {
        if ((userRepository.findByUserAccount(userAccount) != null)) {
            throw DuplicatedRegisterException("This account:$userAccount has been registered")
        }
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
        return if (user == null || user.deleted) {
            false
        }else {
            user.deleted = true
            userRepository.saveAndFlush(user)
            true
        }
    }
}