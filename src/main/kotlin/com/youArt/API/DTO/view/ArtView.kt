package com.youArt.API.DTO.view

import com.youArt.API.entity.Art
import com.youArt.API.entity.Comment
import java.time.LocalDate

class ArtView(
    val title:String,
    val description:String,
    val image: String,
    val createdAt: LocalDate?,
    val updatedAt: LocalDate?,
){
    constructor(art: Art): this (
        title = art.title,
        description = art.description,
        image = art.image,
        createdAt = art.createdAt,
        updatedAt = art.updateAt,
    )
}