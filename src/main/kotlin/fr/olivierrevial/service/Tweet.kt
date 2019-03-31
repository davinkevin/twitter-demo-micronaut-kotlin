package fr.olivierrevial.service

import java.util.*

data class Tweet(var id: Long = 0,
                 var createdAt: Date = Date(0L),
                 var user: String = "defaultUser",
                 var content: String = "defaultContent")