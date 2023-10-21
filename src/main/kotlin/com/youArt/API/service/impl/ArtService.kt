package com.youArt.API.service.impl

import com.youArt.API.entity.Art
import com.youArt.API.repository.ArtRepository
import com.youArt.API.service.IGeneralServices
import org.springframework.stereotype.Service

@Service
class ArtService(
    private val artRepository: ArtRepository,
    private val userService: UserService
): IGeneralServices<Art> {
    override fun create(obj: Art): Art {
        obj.apply {
            user = userService.readById(obj.user?.id!!)
        }
        return artRepository.save(obj)
    }
    override fun readById(id: Long): Art =  this.artRepository.findById(id).orElseThrow {
        RuntimeException("Art with the $id was not found")
    }
    override fun deleteById(id: Long, requestId: Long) {
        val art: Art = this.artRepository.findById(id).orElseThrow {
            RuntimeException("this art does not exist to delete")
        }
        if(art.user?.id == requestId) this.artRepository.delete(art) else
            throw RuntimeException("you are not the creator of this art to delete it")
    }

    //specific functions about this service
    fun deleteSomeById(vararg artIdList: Long, requestId: Long) {
        var art: Art
        artIdList.forEach {
            art = this.artRepository.findById(it).orElseThrow {
                throw RuntimeException("this art does not exist")
            }

            if(art.user?.id == requestId) {
                this.artRepository.delete(art)
            }else {
                throw RuntimeException("you are not the creator of this art to delete it")
            }
        }
    }
}