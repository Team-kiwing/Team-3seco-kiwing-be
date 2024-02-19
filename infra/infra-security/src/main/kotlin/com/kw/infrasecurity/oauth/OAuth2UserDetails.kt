package com.kw.infrasecurity.oauth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class OAuth2UserDetails(id : Long,
                        email : String,
                        authorities: MutableCollection<out GrantedAuthority>) : UserDetails {

    val id : Long = id
    val email : String = email
    val authorities: MutableCollection<out GrantedAuthority> = authorities

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
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
