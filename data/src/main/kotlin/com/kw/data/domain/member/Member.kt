package com.kw.data.domain.member

import com.kw.data.domain.Base
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Member(email: String) : Base() {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "nickname")
    var nickname: String? = null
        protected set

    @Column(name = "profile_image")
    var profileImage: String? = null
        protected set

    @Column(name = "email", nullable = false, updatable = false)
    val email: String = email

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, updatable = false)
    val provider: Provider = Provider.GOOGLE

    @Column(name = "last_logged_in_at", nullable = false)
    var lastLoggedInAt: LocalDateTime = LocalDateTime.now()
        protected set

    @Column(name = "bundle_order", nullable = false)
    var bundleOrder: String = ""
        protected set

    @Enumerated(value = EnumType.STRING)
    var role: MemberRoleType = MemberRoleType.ROLE_USER

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var snsList: MutableList<Sns> = mutableListOf()
        protected set

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var memberTags: MutableList<MemberTag> = mutableListOf()
        protected set

    fun updateMemberNickname(nickname: String) {
        this.nickname = nickname
    }

    fun updateMemberProfileImage(url: String) {
        this.profileImage = url
    }

    fun updateMemberSns(snsList: List<Sns>) {
        if (snsList.size > 3) {
            throw IllegalArgumentException("Sns는 최대 3개까지 지정 가능합니다.")
        }
        this.snsList.clear()
        this.snsList.addAll(snsList)
    }

    fun updateMemberTags(memberTags: List<MemberTag>) {
        if (memberTags.size > 3) {
            throw IllegalArgumentException("관심 태그는 최대 3개까지 지정 가능합니다.")
        }
        this.memberTags.clear()
        this.memberTags.addAll(memberTags)
    }

    fun updateBundleOrder(bundleOrder: String) {
        this.bundleOrder = bundleOrder
    }

    fun updateLastLoggedInAt() {
        this.lastLoggedInAt = LocalDateTime.now()
    }

    enum class MemberRoleType {
        ROLE_USER,
        ROLE_ADMIN
    }

    enum class Provider {
        GOOGLE,
    }
}
