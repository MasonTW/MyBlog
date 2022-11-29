package com.mx.blog.service

import com.mx.blog.DTO.User.UserDTO
import com.mx.blog.DTO.User.UserLoginDTO
import com.mx.blog.DTO.User.UserRegisterDTO
import com.mx.blog.entity.User
import com.mx.blog.exception.DuplicatedRegisterException
import com.mx.blog.exception.NoSuchUserException
import com.mx.blog.repository.UserRepository
import com.mx.blog.utils.JWTUtils
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun findUserById(id: Long): UserDTO {
        return userRepository.findByIdAndDeleted(id, false)?.let {
            User.toUserDTO(it)
        } ?: throw NoSuchUserException("no such user")

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

    fun login(userLoginDTO: UserLoginDTO): Boolean {
        return userRepository.findByUserAccountAndUserPassword(
            userAccount = userLoginDTO.userAccount,
            userPassword = userLoginDTO.userPassword
        )?.let {
            val user = UserDTO(userId = it.id, userName = it.userName)
            val token = JWTUtils.getToken(user = user)
            println(token)
            return true
        } ?: false
    }

    fun deleteUser(userId: Long): Boolean {
        //todo verify permission
        val user = userRepository.findById(userId).get()
        return if (user == null || user.deleted) {
            false
        } else {
            user.deleted = true
            userRepository.saveAndFlush(user)
            true
        }
    }

    private fun checkAccount(userAccount: String) {
        if ((userRepository.findByUserAccount(userAccount) != null)) {
            throw DuplicatedRegisterException("This account:$userAccount has been registered")
        }
    }
}
