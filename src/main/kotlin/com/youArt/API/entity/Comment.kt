package com.youArt.API.entity

import com.youArt.API.enummeration.Status
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDate
@Entity
data class Comment(
    @Enumerated
    var status: Status = Status.IN_PROGRESS,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(nullable = false)
    val userName:String,

    @Column(nullable = false)
    var text: String,

    @Column(nullable = false)
    val createdAt: LocalDate,

    @Column(nullable = false)
    var updatedAt: LocalDate,

    @ManyToOne
    val art: Art? = null
)
