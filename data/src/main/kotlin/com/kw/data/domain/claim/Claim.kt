package com.kw.data.domain.claim

import jakarta.persistence.*

@Entity
class Claim(content : String) {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

    @Column(name = "content", nullable = false, updatable = false)
    var content : String = content
}
