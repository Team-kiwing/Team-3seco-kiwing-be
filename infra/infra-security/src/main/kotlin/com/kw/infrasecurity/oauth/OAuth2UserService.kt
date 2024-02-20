package com.kw.infrasecurity.oauth

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class OAuth2UserService : DefaultOAuth2UserService(){
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val registrationId = userRequest!!.clientRegistration.registrationId

        val attributes: MutableMap<String, Any>
        val oAuth2User = super.loadUser(userRequest)
        attributes = HashMap(oAuth2User.attributes)

        attributes["provider"] = registrationId

        return DefaultOAuth2User(emptyList(), attributes, "provider")
    }
}
