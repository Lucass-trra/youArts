package com.youArt.API.entity

import com.youArt.API.enummeration.Status
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
@Entity
@EntityListeners(AuditingEntityListener::class)
data class Comment(
    @Enumerated
    var status: Status = Status.IN_PROGRESS,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val userName:String,

    @Column(nullable = false)
    var text: String,

    @CreatedDate
    @Column(nullable = true, updatable = false)
    var createdAt: LocalDate? = null,

    @LastModifiedDate
    @Column(nullable = true)
    var updatedAt: LocalDate? = null,

    @ManyToOne
    var art: Art? = null,

    @ManyToOne
    var user: User? = null
)
