package fr.olivierrevial

import twitter4j.Query

data class TwitterResult(var tweets: List<Tweet>? = emptyList(), var nextQuery: Query? = null)