package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureException() {
    install(StatusPages) {
        exception<UserNotFoundException> { call, cause ->
            call.respondText(text = "404: ${cause.message}", status = HttpStatusCode.NotFound)
        }

        exception<Throwable> { call, cause ->
            cause.printStackTrace()
            call.respondText(text = "500: ${cause.message}", status = HttpStatusCode.InternalServerError)
        }
    }
}

data class UserNotFoundException(
    override val message: String = "User not found",
) : RuntimeException(message)