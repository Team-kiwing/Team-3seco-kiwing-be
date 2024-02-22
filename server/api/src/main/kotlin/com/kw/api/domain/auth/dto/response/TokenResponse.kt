package com.kw.api.domain.auth.dto.response

data class TokenResponse(val accessToken : String,
    val refreshToken : String)
