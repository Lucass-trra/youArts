package com.youArt.API.repository

import com.youArt.API.entity.Art
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface ArtRepository: JpaRepository<Art,Long>{
    @Query(value = "SELECT * FROM art WHERE USER_ID = ?1", nativeQuery = true)
    fun readAllByUserId(userId: Long):List<Art>

}