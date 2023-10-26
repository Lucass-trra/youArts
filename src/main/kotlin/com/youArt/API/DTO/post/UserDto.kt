package com.youArt.API.DTO.post

import com.youArt.API.entity.User

class UserDto(
    val name: String,
    val phoneNumber: Long,
    val emailAddress: String,
    val password: String
) {
    fun toEntity(): User = User(
        name = this.name,
        phoneNumber = this.phoneNumber,
        emailAddress = this.emailAddress,
        password = this.password
    )
}