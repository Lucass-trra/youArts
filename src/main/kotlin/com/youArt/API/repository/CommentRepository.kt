package com.youArt.API.repository

import com.youArt.API.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface CommentRepository: JpaRepository<Comment,Long> {
    @Query(value = "SELECT * FROM comment WHERE ART_ID = ?1", nativeQuery = true)
    fun readAllByArtId(artId: Long):List<Comment>

    @Query(value = "SELECT * FROM comment WHERE USER_ID = ?1", nativeQuery = true)
    fun readAllByUserId(userId: Long):List<Comment>
}