package com.youArt.API.exception

class ResourceNotFoundException(override val message:String?): RuntimeException(message) {
}