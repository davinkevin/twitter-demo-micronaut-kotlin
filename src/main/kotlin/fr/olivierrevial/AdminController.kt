package fr.olivierrevial

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.validation.Validated
import io.reactivex.Flowable
import io.reactivex.Single
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Controller("/admin")
class AdminController(private val mongoService: MongoService) {

    // TODO Add authent
    @Get("/mongo/reset")
    fun reset(): Single<String> {
        return mongoService.deleteAll()
    }
}