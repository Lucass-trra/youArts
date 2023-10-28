package com.youArt.API.DTO.post

import com.youArt.API.entity.Art
import com.youArt.API.entity.Comment
import com.youArt.API.entity.User
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.time.LocalDate

class CommentDto(
    @field:NotEmpty(message = "input empty or null, please type the field")
    val userName: String,

    @field:NotEmpty(message = "input empty or null, please type the field")
    val text: String,

    @field:Positive(message = "this id must be positive and bigger then 0")
    @field:NotNull(message = "input empty or null, please type the field")
    val artId: Long,

    @field:Positive(message = "this id must be positive and bigger then 0")
    @field:NotNull(message = "input empty or null, please type the field")
    val userId: Long
) {
    fun toEntity(): Comment = Comment(
        userName = this.userName,
        text = this.text,
        art = Art(id = this.artId),
        user = User(id = this.userId)
    )
}