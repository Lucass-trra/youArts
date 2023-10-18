package com.youArt.API.entity

import com.youArt.API.enummeration.Status
import java.time.LocalDate
data class Art(
    val id: Long? = null,
    var status: Status = Status.IN_PROGRESS,
    var title: String,
    var description: String,
    var image: String,
    val createdAt: LocalDate,
    var updateAt: LocalDate
) {
    private var commments: MutableList<Comment> = mutableListOf()
}
