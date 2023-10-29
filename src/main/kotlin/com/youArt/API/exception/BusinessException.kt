package com.youArt.API.exception

class BusinessException(override val message:String?): RuntimeException(message) {
}