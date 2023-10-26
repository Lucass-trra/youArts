package com.youArt.API.DTO.view

import com.youArt.API.entity.Art

class ArtListView(
    val title: String,
    val image: String
) {
    constructor(art: Art): this(
        title = art.title,
        image = art.image
    )
}