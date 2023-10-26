package com.youArt.API.entity

import com.youArt.API.enummeration.Status
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Enumerated
    var status: Status = Status.IN_PROGRESS,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false, unique = true)
    var phoneNumber: Long = 0,

    @Column(nullable = false, unique = true)
    val emailAddress: String = "",

    @Column(nullable = false)
    var password: String = "",

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], mappedBy = "user")
    private val arts: MutableList<Art> = mutableListOf(),

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], mappedBy = "user")
    private val comments: MutableList<Comment> = mutableListOf()

)




