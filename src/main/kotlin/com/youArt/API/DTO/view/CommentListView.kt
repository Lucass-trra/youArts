package com.youArt.API.DTO.view

import com.youArt.API.entity.Comment

class CommentListView(
    val userName: String,
    val text: String,
) {
    constructor(comment: Comment):this (
        userName = comment.userName,
        text = comment.text
    )

}