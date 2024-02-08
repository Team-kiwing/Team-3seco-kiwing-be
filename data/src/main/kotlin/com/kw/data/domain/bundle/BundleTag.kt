package com.kw.data.domain.bundle

import com.kw.data.domain.Base
import jakarta.persistence.*

@Entity
class BundleTag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Int? = null,
) : Base()
