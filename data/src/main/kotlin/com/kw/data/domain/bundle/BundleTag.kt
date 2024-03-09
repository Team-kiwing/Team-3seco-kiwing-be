package com.kw.data.domain.bundle

import com.kw.data.domain.Base
import com.kw.data.domain.tag.Tag
import jakarta.persistence.*

@Entity
class BundleTag(bundle: Bundle, tag: Tag) : Base() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_id", nullable = false)
    val bundle: Bundle = bundle

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    val tag: Tag = tag
}
