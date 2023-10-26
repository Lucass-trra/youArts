package com.youArt.API.DTO.update

import com.youArt.API.entity.Art
import com.youArt.API.entity.Comment
import com.youArt.API.entity.User

class UserUpdateDto(
    val name: String,
    val phoneNumber: Long,
    val password: String
){
    fun toEntity(user: User):User {
        user.name = this.name
        user.phoneNumber = this.phoneNumber
        user.password = this.password

        return user
    }
}