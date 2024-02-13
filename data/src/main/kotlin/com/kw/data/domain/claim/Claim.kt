package com.kw.data.domain.claim

import com.kw.data.domain.Base
import jakarta.persistence.*

@Entity
class Claim(content : String) : Base() {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

    @Column(name = "content", nullable = false, updatable = false)
    var content : String = content
}
