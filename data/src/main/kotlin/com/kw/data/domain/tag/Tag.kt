package com.kw.data.domain.tag

import com.kw.data.domain.Base
import jakarta.persistence.*

@Entity
class Tag(content : String) {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null

    @Column(name = "content", nullable = false, updatable = true)
    var content : String = content
}
