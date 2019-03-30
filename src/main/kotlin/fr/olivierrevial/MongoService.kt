package fr.olivierrevial

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.Success
import io.reactivex.Flowable
import io.reactivex.Single
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class MongoService(private val mongo: MongoClient) {
    companion object {
        val LOG = LoggerFactory.getLogger(MongoService::class.java)
        val TWEETS_COLLECTION = "tweets"
    }

    fun list(): Flowable<Tweet> {
        return Flowable.fromPublisher<Tweet>(getCollection().find())
    }

    fun save(tweets: List<Tweet>): Single<String> {
        LOG.info("""Saving ${tweets.size} tweets in Mongo...""")
        return Single.fromPublisher<Success>(getCollection().insertMany(tweets))
                .map { """Successfuly saved ${tweets.size} tweets to Mongo...""" }
    }

    fun deleteAll(): Single<String> {
        LOG.info("""Deleting entire  $TWEETS_COLLECTION collection from Mongo...""")
        return Single.fromPublisher<Success>(getCollection().drop())
                .map { "Successfuly deleted entire $TWEETS_COLLECTION from Mongo..." }
    }

    private fun getCollection(): MongoCollection<Tweet> {
        return mongo
                .getDatabase("micronaut")
                .getCollection<Tweet>(TWEETS_COLLECTION, Tweet::class.java)
    }
}