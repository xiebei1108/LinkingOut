package com.sao.service

import com.sao.domain.model.User

interface UserService {

    //register insert User table
    fun insert(user : User)

    //login
    fun findByUsername(userName :String) : User

    //find user by his uid
    fun findByUid(uid :Long) : User

    //find all Users
    fun findAllUsers() : Iterable<User>

    fun findByTel(tel:String): User

    fun existByTel(tel:String):Boolean
}