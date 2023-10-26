package com.youArt.API.DTO.post

import com.youArt.API.entity.Art
import com.youArt.API.entity.User

data class ArtDto(
    val title: String,
    val description: String,
    val image: String,
    val userId: Long
) {
    fun toEntity(): Art = Art(
        title = this.title,
        description = this.description,
        image = this.image,
        user = User(id = this.userId)
    )
}