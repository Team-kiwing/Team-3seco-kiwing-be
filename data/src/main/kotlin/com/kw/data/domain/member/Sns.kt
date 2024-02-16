package com.kw.data.domain.member

import com.kw.data.domain.Base
import jakarta.persistence.*

@Entity
class Sns(name: String, url: String) : Base() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Long? = null

    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "url", nullable = false)
    var url: String = url
        protected set
}
