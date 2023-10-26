package com.youArt.API.DTO.view

import com.youArt.API.entity.Comment
import java.time.LocalDate

class CommentView(
    val userName: String,
    val text: String,
    val createdAt: LocalDate?,
    val updatedAt: LocalDate?
) {
    constructor(comment: Comment):this (
        userName = comment.userName,
        text = comment.text,
        createdAt = comment.createdAt,
        updatedAt = comment.updatedAt
    )

}