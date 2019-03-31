package fr.olivierrevial.controller

import fr.olivierrevial.service.MongoService
import fr.olivierrevial.service.Tweet
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.validation.Validated
import io.reactivex.Flowable
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Validated
@Controller("/tweets")
@Secured(SecurityRule.IS_ANONYMOUS)
class TweetController(private val mongoService: MongoService) {

    @Get("/")
    fun list(@NotNull @Min(1) @Max(100) total: Int?): Flowable<Tweet> {
        return mongoService.list(total)
    }
}