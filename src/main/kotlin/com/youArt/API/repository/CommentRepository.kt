package com.youArt.API.repository

import com.youArt.API.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment,Long> {
}