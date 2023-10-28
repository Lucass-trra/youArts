package com.youArt.API.DTO.post

import com.youArt.API.entity.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

class UserDto(
    @field:NotEmpty(message = "input empty or null, please type the fields")
    val name: String,

    @field:NotNull(message = "input empty or null, please type the field")
    val phoneNumber: Long,

    @field:Email(message = "invalid email, please, type again")
    @field:NotEmpty(message = "input empty or null, please type the field")
    val emailAddress: String,

    @field:NotEmpty(message = "input empty or null, please type the field")
    val password: String
) {
    fun toEntity(): User = User(
        name = this.name,
        phoneNumber = this.phoneNumber,
        emailAddress = this.emailAddress,
        password = this.password
    )
}