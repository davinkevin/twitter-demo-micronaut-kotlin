package fr.olivierrevial

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("fr.olivierrevial")
                .mainClass(Application.javaClass)
                .start()
    }
}