package com.example

import io.ktor.server.application.*
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.named
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()
        modules(
            userModule,
        )
    }
}

val userModule = module {
    single { } withOptions {
        // named function is qualifier for dependency injection
        named("singleton")
        createdAtStart()
    }

    factory { } withOptions {
        named("alwaysNew")
        createdAtStart()
    }
}