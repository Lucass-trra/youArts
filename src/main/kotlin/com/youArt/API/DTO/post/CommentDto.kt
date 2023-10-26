package com.youArt.API.DTO.post

import com.youArt.API.entity.Art
import com.youArt.API.entity.Comment
import com.youArt.API.entity.User
import java.time.LocalDate

class CommentDto(
    val userName: String,
    val text: String,
    val artId: Long,
    val userId: Long
) {
    fun toEntity(): Comment = Comment(
        userName = this.userName,
        text = this.text,
        art = Art(id = this.artId),
        user = User(id = this.userId)
    )
}