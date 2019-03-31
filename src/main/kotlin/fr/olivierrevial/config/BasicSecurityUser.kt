package fr.olivierrevial.config

import io.micronaut.validation.Validated
import javax.validation.constraints.NotBlank

class BasicSecurityUser {

    @NotBlank
    var user: String? = null

    @NotBlank
    var password: String? = null

    @NotBlank
    var role: String? = null

    fun toMap(): MutableMap<String, Any> {
        val m = HashMap<String, Any>()
        if (user != null) {
            m["user"] = user!!
        }
        if (password != null) {
            m["password"] = password!!
        }
        if (role != null) {
            m["role"] = role!!
        }
        return m
    }
}