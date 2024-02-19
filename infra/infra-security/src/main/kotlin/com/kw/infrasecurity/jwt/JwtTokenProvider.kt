package com.kw.infrasecurity.jwt

import com.kw.infrasecurity.oauth.OAuth2UserDetails
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*
import java.util.stream.Collectors
import javax.crypto.SecretKey

@Component
class JwtTokenProvider {
    @Value("\${jwt.secret-key}")
    lateinit var secretKey : String

    @Value("\${jwt.expiry-seconds}")
    var expirySeconds : Int? = null

    @Value("\${jwt.refresh-expiry-seconds}")
    var refreshExpirySeconds : Int? = null

    fun generateAccessToken(userDetails : OAuth2UserDetails) : String {
        val expirationTime = Instant.now().plusSeconds(expirySeconds!!.toLong())

        val authorities = userDetails.getAuthorities().stream()
            .map { authority: GrantedAuthority -> authority.authority }
            .collect(Collectors.joining(","))

        return Jwts.builder()
            .claim("id", userDetails.id)
            .subject(userDetails.username)
            .issuedAt(Date.from(Instant.now()))
            .expiration(Date.from(expirationTime))
            .claim("authorities", authorities)
            .signWith(getKey())
            .compact()
    }

    fun generateRefreshToken(): String {
        val expirationTime = Instant.now().plusSeconds(refreshExpirySeconds!!.toLong())
        return Jwts.builder()
            .issuedAt(Date.from(Instant.now()))
            .expiration(Date.from(expirationTime))
            .signWith(getKey())
            .compact()
    }

    fun getAuthentication(accessToken: String?): Authentication? {
        val claims = parseClaims(accessToken!!)
        val authorities =
                Arrays.stream(claims!!["authorities"].toString().split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray())
                    .map { role: String? ->
                        SimpleGrantedAuthority(
                            role
                        )
                    }
                    .toList()

        val principal = OAuth2UserDetails(
            claims["id"] as Long,
            claims.subject,
            authorities
        )
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }


    fun validateAccessToken(token: String?) {
        Jwts.parser().verifyWith(getKey()).build().parse(token)
    }

    private fun parseClaims(accessToken: String): Claims? {
        return try {
            Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(accessToken)
                .payload
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }

    private fun getKey(): SecretKey {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }
}
