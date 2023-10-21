package com.youArt.API.service

import com.youArt.API.entity.Art

interface IGeneralServices <T> {
    fun create( obj: T ): T
    fun readById( id:Long ): T
    fun deleteById( id: Long, requestId: Long )

}