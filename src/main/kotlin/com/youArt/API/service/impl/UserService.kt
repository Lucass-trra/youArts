package com.youArt.API.service.impl

import com.youArt.API.entity.Art
import com.youArt.API.entity.User
import com.youArt.API.repository.UserRepository
import com.youArt.API.service.IGeneralServices
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
): IGeneralServices<User> {
    override fun create(obj: User): User = this.userRepository.save(obj)
    override fun readById(id: Long): User = this.userRepository.findById(id).orElseThrow {
        throw RuntimeException("the user with id $id was not found")
    }
    override fun deleteById(id: Long, requestId: Long) {
        val user: User = this.userRepository.findById(id).orElseThrow {
            throw RuntimeException("this user does not exist to delete")
        }

        if(user.id == requestId) {
            this.userRepository.delete(user)
        } else {
            throw RuntimeException("you are not the user that create this account to delete it")
        }
    }
}