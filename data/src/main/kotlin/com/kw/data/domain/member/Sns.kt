package com.kw.data.domain.member

import com.kw.data.domain.Base
import jakarta.persistence.*

@Entity
class Sns(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Int? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "url", nullable = false)
    var url: String,
) : Base()
