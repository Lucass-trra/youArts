package com.youArt.API.entity

import com.youArt.API.enummeration.Status
import jakarta.persistence.*
import java.time.LocalDate
@Entity
data class Art(
    @Enumerated
    var status: Status = Status.IN_PROGRESS,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(nullable = false)
    var title: String = "",

    @Column(nullable = false)
    var description: String = "",

    @Column(nullable = false)
    var image: String = "",

    @Column(nullable = false)
    val createdAt: LocalDate,

    @Column(nullable = false)
    var updateAt: LocalDate,

    @ManyToOne
    var user: User? = null,

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], mappedBy = "art")
    private var commments: MutableList<Comment> = mutableListOf()
)

