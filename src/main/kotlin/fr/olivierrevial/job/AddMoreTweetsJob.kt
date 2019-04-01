package fr.olivierrevial.job

import fr.olivierrevial.service.MongoService
import fr.olivierrevial.service.TwitterResult
import fr.olivierrevial.service.TwitterService
import io.micronaut.scheduling.annotation.Scheduled
import io.reactivex.Single
import org.slf4j.LoggerFactory
import twitter4j.Query
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import javax.inject.Singleton


@Singleton
class AddMoreTweetsJob(private val twitterService: TwitterService, private val mongoService: MongoService) {
    companion object {
        private val LOG = LoggerFactory.getLogger(AddMoreTweetsJob::class.java)
    }

    var nextQuery: Query? = null

    @Scheduled(fixedRate = "10s", initialDelay = "1m")
    fun addTweets() {
        LOG.info("Will now add more tweets...")

        val result =
            if (nextQuery != null) twitterService.listTweets(nextQuery!!)
            else twitterService.listTweets(initialDate())

        Single.just(result)
                .doOnSuccess { r -> nextQuery = r.nextQuery }
                .map(TwitterResult::tweets)
                .doOnSuccess { t -> LOG.info("""Got ${t?.size} items from ${t?.firstOrNull()?.createdAt} to ${t?.lastOrNull()?.createdAt}""") }
                .flatMap { t -> mongoService.save(t) }
                .blockingGet()
    }

    // 'Cause Twitter only allows 7-days search on standard accounts
    fun initialDate(): Date {
        return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
    }
}