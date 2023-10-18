package com.youArt.API.entity

import com.youArt.API.enummeration.Status

data class User(
    val id: Long? = null,
    var status: Status = Status.IN_PROGRESS,
    var name: String = "",
    var phoneNumber: Long = 0,
    var password: String = "",
    val emailAddress: String = "",
) {
    private val arts: MutableList<Art> = mutableListOf()
}


