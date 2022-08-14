package com.mx.blog.entity

import javax.persistence.*

@Entity
class User(
    @Id @GeneratedValue var id: Long = -1,
    var userAccount: String,
    var userName: String,
    var userPassword: String,
    var userRegisterTime: String,
    @OneToMany(targetEntity = Collection::class, cascade = [CascadeType.ALL], mappedBy = "userId")
    var collections: List<Collection> = mutableListOf()
    )
