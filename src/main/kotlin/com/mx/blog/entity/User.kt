package com.mx.blog.entity

import com.mx.blog.DTO.User.UserDTO
import com.mx.blog.DTO.User.UserRegisterDTO
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long = -1,
    var userAccount: String,
    var userName: String,
    var userPassword: String,
    var userRegisterTime: String,
    @OneToMany(targetEntity = Collection::class, cascade = [CascadeType.ALL], mappedBy = "userId")
    var collections: List<Collection> = mutableListOf(),
    var deleted: Boolean = false,
) {
    companion object {
        fun toUserDTO(user: User): UserDTO {
            return UserDTO(
                userId = user.id,
                userName = user.userName
            )
        }

        fun toUser(userRegisterDTO: UserRegisterDTO): User {
           return User(
               userName = userRegisterDTO.userName,
               userPassword = userRegisterDTO.userPassword,
               userRegisterTime = LocalDateTime.now().toString(),
               userAccount = userRegisterDTO.userAccount,
           )
        }
    }
}
