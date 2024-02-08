package com.kw.data.domain

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class Base(

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    var updatedAt: LocalDateTime? = LocalDateTime.now()
)
