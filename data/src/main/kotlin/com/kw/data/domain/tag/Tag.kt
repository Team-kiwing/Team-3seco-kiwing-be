package com.kw.data.domain.tag

import jakarta.persistence.*

@Entity
class Tag(content: String) {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "name", nullable = false, updatable = true)
    var name: String = content
}
