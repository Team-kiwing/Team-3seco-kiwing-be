package com.kw.data.domain.member

import com.kw.data.domain.Base
import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime

@Entity
@SQLRestriction("deleted_at is null")
class Member(email: String) : Base() {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
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

    @Column(name = "last_logined_at", nullable = false)
    var lastLoginedAt: LocalDateTime = LocalDateTime.now()
        protected set

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
        protected set

    var memberRoles : MutableList<MemberRoleType> = mutableListOf(MemberRoleType.ROLE_USER)

    @Embedded
    var socialLinks: SocialLinks? = null
        protected set

    fun updateMemberNickname(nickname: String) {
        this.nickname = nickname
    }

    fun updateMemberProfileImage(url: String) {
        this.profileImage = url
    }

    fun withdrawMember() {
        this.deletedAt = LocalDateTime.now()
    }

    fun updateMemberSocialLinks(socialLinks: SocialLinks) {
        this.socialLinks = socialLinks
    }

    enum class MemberRoleType {
        ROLE_USER,
        ROLE_ADMIN
    }

    enum class Provider {
        GOOGLE,
    }

    @Embeddable
    class SocialLinks(link1: String,
                      link2: String,
                      link3: String) {
    }
}
