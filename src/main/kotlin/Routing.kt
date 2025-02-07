package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import java.time.LocalDate

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
}

@JvmInline
value class UserName(val value: String)

data class CreateUserRequest(
    val name: UserName,
    val age: Int,
    val birthday: LocalDate,
)

data class UserResponse(
    val id: Long,
    val name: UserName,
    val age: Int,
    val birthday: LocalDate,
)