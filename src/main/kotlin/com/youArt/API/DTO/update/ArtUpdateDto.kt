package com.youArt.API.DTO.update

import com.youArt.API.entity.Art

class ArtUpdateDto(
    val title: String,
    val description: String,
    val image: String
) {
    fun toEntity(art: Art):Art {
        art.title = this.title
        art.description = this.description
        art.image = this.image

        return art
    }
}