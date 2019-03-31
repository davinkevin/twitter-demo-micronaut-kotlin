package fr.olivierrevial.config

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("app.security")
class BasicSecurity {

    var users: List<BasicSecurityUser>? = null
}