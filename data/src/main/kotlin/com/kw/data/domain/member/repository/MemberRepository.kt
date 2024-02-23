package com.kw.data.domain.member.repository

import com.kw.data.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findMemberByEmail(email : String) : Member?
}
