package fr.olivierrevial.config


import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.exceptions.ConfigurationException
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder
import javax.inject.Singleton

@Singleton
@Factory
class TwitterFactoryBean() {

    @Bean
    @Singleton
    internal fun twitter(): Twitter {
        val twitterConfig = ConfigurationBuilder().build()
        if (twitterConfig.getOAuthAccessToken() == null) {
            throw ConfigurationException("Missing Twitter OAuthAccessToken")
        }
        if (twitterConfig.getOAuthAccessTokenSecret() == null) {
            throw ConfigurationException("Missing Twitter OAuthAccessTokenSecret")
        }
        if (twitterConfig.getOAuthConsumerKey() == null) {
            throw ConfigurationException("Missing Twitter OAuthConsumerKey")
        }
        if (twitterConfig.getOAuthConsumerSecret() == null) {
            throw ConfigurationException("Missing Twitter OAuthConsumerSecret")
        }
        val factory = TwitterFactory(twitterConfig)
        return factory.getInstance()
    }
}