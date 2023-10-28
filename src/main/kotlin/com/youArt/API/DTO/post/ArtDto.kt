package com.youArt.API.DTO.post

import com.youArt.API.entity.Art
import com.youArt.API.entity.User
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class ArtDto(
    @field:NotEmpty(message = "input empty or null, please type the fields")
    val title: String,

    @field:NotEmpty(message = "input empty or null, please type the fields")
    val description: String,

    @field:NotEmpty(message = "input empty or null, please type the fields")
    val image: String,

    @field:Positive(message = "this id must be positive and bigger then 0")
    @field:NotNull(message = "input empty or null, please type the field")
    val userId: Long
) {
    fun toEntity(): Art = Art(
        title = this.title,
        description = this.description,
        image = this.image,
        user = User(id = this.userId)
    )
}