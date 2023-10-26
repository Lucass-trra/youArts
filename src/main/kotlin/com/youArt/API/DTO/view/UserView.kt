package com.youArt.API.DTO.view

import com.youArt.API.entity.Art
import com.youArt.API.entity.Comment
import com.youArt.API.entity.User

class UserView(
    val name: String,
    val phoneNumber: Long,
    val emailAddress: String,
) {

    constructor(user: User): this (
        name = user.name,
        phoneNumber = user.phoneNumber,
        emailAddress = user.emailAddress,
    )
}