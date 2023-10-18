package com.youArt.API.entity

import com.youArt.API.enummeration.Status
import java.time.LocalDate

data class Comment(
    val id: Long? = null,
    var status: Status = Status.IN_PROGRESS,
    val userName:String,
    var text: String,
    var artId: Long,
    val createdAt: LocalDate,
    var updatedAt: LocalDate
) {

}
