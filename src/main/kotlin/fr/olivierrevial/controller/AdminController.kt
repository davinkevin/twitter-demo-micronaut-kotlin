package fr.olivierrevial.controller

import fr.olivierrevial.service.MongoService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import io.reactivex.Single

@Controller("/admin")
@Secured(SecurityRule.IS_AUTHENTICATED)
class AdminController(private val mongoService: MongoService) {

    @Get("/anonymous")
    @Secured(SecurityRule.IS_ANONYMOUS)
    fun anonymous(): String {
        return "This is an anonymous endpoint"
    }

    @Get("/mongo/reset")
    @Secured("ROLE_ADMIN")
    fun reset(): Single<String> {
        return mongoService.deleteAll()
    }

    @Get("/action/edit")
    @Secured("ROLE_EDITOR")
    fun edit(): String {
        return "You have the right to edit this..."
    }

    @Produces(MediaType.TEXT_PLAIN)
    @Get("/authenticated")
    fun authenticated(authentication: Authentication): String {
        return authentication.name + " is authenticated"
    }
}