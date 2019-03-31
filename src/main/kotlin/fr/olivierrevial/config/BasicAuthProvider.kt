package fr.olivierrevial.config

import io.micronaut.context.annotation.Value
import io.micronaut.security.authentication.*
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import javax.inject.Singleton


@Singleton
class BasicAuthProvider() : AuthenticationProvider {

    @Value("\${app.security.admin.user}")
    var admin: String? = null

    @Value("\${app.security.admin.password}")
    var adminPassword: String? = null

    @Value("\${app.security.admin.role}")
    var adminRole: String? = null

    override fun authenticate(authenticationRequest: AuthenticationRequest<*, *>): Publisher<AuthenticationResponse> {
        return if (authenticationRequest.identity.equals(admin) && authenticationRequest.secret.equals(adminPassword)) {
            Flowable.just(UserDetails(authenticationRequest.identity as String, listOf(adminRole)))
        } else Flowable.just(AuthenticationFailed())
    }

//    override fun authenticate(authenticationRequest: AuthenticationRequest<*, *>): Publisher<AuthenticationResponse> {
//        val authUser = basicSecurity.users!!
//                .filter { u -> u.user == authenticationRequest.identity }
//                .filter { u -> u.password == authenticationRequest.secret }
//                .map { u -> UserDetails(authenticationRequest.identity as String, listOf(u.role)) }
//                .firstOrNull()
//        return if (authUser != null) Flowable.just(authUser) else Flowable.just(AuthenticationFailed())
//    }
}