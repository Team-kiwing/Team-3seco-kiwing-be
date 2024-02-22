package com.kw.infrasecurity.oauth

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class OAuth2UserDetails(id : Long,
                        email : String,
                        authorities: List<SimpleGrantedAuthority>) : UserDetails {

    val id : Long = id
    val email : String = email
    val authorities: List<SimpleGrantedAuthority> = authorities

    override fun getAuthorities(): List<SimpleGrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String? {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
