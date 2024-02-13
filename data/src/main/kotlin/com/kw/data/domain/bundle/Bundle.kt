package com.kw.data.domain.bundle

import com.kw.data.domain.Base
import com.kw.data.domain.tag.Tag
import jakarta.persistence.*

@Entity
class Bundle(
    name: String,
    shareType: ShareType
) : Base() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Long? = null

    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "share_type", nullable = false)
    var shareType: ShareType = shareType
        protected set

    @Column(name = "share_count", nullable = false)
    var shareCount: Long? = 0
        protected set

    @OneToMany(mappedBy = "bundle", cascade = [CascadeType.ALL], orphanRemoval = true)
    var bundleTags: MutableList<BundleTag> = mutableListOf()

    enum class ShareType {
        PUBLIC,
        PRIVATE,
    }

    fun addBundleTag(tag: Tag) {
        bundleTags.add(BundleTag(this, tag))
    }

    fun updateNameAndShareType(name: String, shareType: ShareType) {
        this.name = name
        this.shareType = shareType
    }

    fun updateBundleTags(tags: List<Tag>) {
        this.bundleTags = tags.map { BundleTag(this, it) }.toMutableList()
    }
}


