package com.youArt.API.repository

import com.youArt.API.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User,Long> {

}