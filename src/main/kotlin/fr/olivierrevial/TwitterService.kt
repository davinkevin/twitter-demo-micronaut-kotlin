package fr.olivierrevial

import io.reactivex.Flowable
import org.slf4j.LoggerFactory
import twitter4j.Query
import twitter4j.Twitter
import twitter4j.TwitterException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Singleton
class TwitterService(private val twitter: Twitter) {
    companion object {
        val LOG = LoggerFactory.getLogger(TwitterService::class.java)
        const val RESULTS_PER_QUERY = 10
        const val SEARCH_STRING = "devoxx"
    }

    fun listTweets(until: Date): TwitterResult {
        val dateAsString = SimpleDateFormat("YYYY-MM-dd").format(until)
        LOG.info("""Requesting $RESULTS_PER_QUERY tweets before $dateAsString""")

        return getTweets(Query(SEARCH_STRING).until(dateAsString))
    }

    fun listTweets(query: Query): TwitterResult {
        LOG.info("""Requesting $RESULTS_PER_QUERY tweets with query ${query.query}""")

        return getTweets(query)
    }

    private fun getTweets(query: Query): TwitterResult {
        return try {
            val searchResult = twitter.search(query.count(RESULTS_PER_QUERY))
            val tweets = searchResult.tweets
                .map { t ->
                    Tweet(
                        t.id,
                        t.createdAt,
                        t.user.screenName,
                        t.text)
                }
            TwitterResult(tweets, searchResult.nextQuery())
        } catch (e: TwitterException) {
            TwitterResult()
        }

    }
}
