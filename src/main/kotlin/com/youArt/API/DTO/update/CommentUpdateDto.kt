package com.youArt.API.DTO.update

import com.youArt.API.entity.Comment

class CommentUpdateDto(
    val text: String
) {
    fun toEntity(comment: Comment):Comment {
        comment.text = this.text
        return comment
    }
}