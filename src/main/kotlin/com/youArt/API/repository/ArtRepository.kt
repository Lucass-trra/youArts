package com.youArt.API.repository

import com.youArt.API.entity.Art
import org.springframework.data.jpa.repository.JpaRepository

interface ArtRepository: JpaRepository<Art,Long>{
}