package com.kw.data.domain.member

import com.kw.data.domain.Base
import com.kw.data.domain.tag.Tag
import jakarta.persistence.*

@Entity
class MemberTag(member: Member, tag: Tag): Base() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member = member

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    val tag: Tag = tag
}
