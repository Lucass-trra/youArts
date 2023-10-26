package com.youArt.API.entity

import com.youArt.API.enummeration.Status
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
@Entity
@EntityListeners(AuditingEntityListener::class)
data class Art(
    @Enumerated
    var status: Status = Status.IN_PROGRESS,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var title: String = "",

    @Column(nullable = false)
    var description: String = "",

    @Column(nullable = false)
    var image: String = "",

    @CreatedDate
    @Column(nullable = true, updatable = false)
    val createdAt: LocalDate? = null,

    @LastModifiedDate
    @Column(nullable = true)
    var updateAt: LocalDate? = null,

    @ManyToOne
    var user: User? = null,

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], mappedBy = "art")
    private var commments: MutableList<Comment> = mutableListOf()
)

