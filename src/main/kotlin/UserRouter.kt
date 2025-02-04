package com.example

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.ktor.ext.inject
import java.time.LocalDate

fun Route.user() {
    val userRepository by application.inject<UserRepository>()

    get("/") {
        call.respondText { "Hello World" }
    }

    post("/api/v1/users") {
        val request = call.receive<UserRequest>()

        newSuspendedTransaction {
            with(request) {
                userRepository.create(name, age, birthday)
            }
        }

        call.respondText(status = HttpStatusCode.Created) { "성공" }
    }

    get("/api/v1/users/{username}") {
        val username = call.pathParameters["username"]
            ?.let(Username::of)
            ?: throw IllegalArgumentException("'username' is missing")

        val minAge = call.queryParameters["minAge"]?.toInt()
        val maxAge = call.queryParameters["maxAge"]?.toInt()
    }

    get("/api/v2/users/{username}") {
        val username = call.pathParameters.username

        val request = call.modelAttributes<GetUserRequest>()

        val response =
            newSuspendedTransaction {
                userRepository.findOne(
                    name = username,
                    minAge = request.minAge,
                    maxAge = request.maxAge,
                )
            }

        call.respond(response)
    }
}

@JvmInline
value class Username(
    val value: String,
) {
    companion object {
        fun of(value: String) = Username(value)
    }

    init {
        // validation
    }
}

data class UserRequest(
    val name: Username,
    val age: Int,
    val birthday: LocalDate,
)

data class GetUserRequest(
    val minAge: Int?,
    val maxAge: Int?,
)

data class UserResponse(
    val id: Long,
    val name: Username,
    val age: Int,
    val birthday: LocalDate,
)

val Parameters.username: Username
    get() = this["username"]
        ?.let(Username::of)
        ?: throw IllegalArgumentException("'username' is missing")

inline fun <reified T : Any> RoutingCall.modelAttributes(): T {
    require(T::class.isData) { "Client에게 값을 받을 때는 Data Class만 지원합니다." }

    val map =
        queryParameters.entries().associate { (key, value) ->
            if (value.size == 1) key to value.first()
            else key to value
        }

    val mapper = application.attributes[objectMapperKey]

    return try {
        mapper.convertValue(map, T::class.java)
    } catch (cause: Exception) {
        throw IllegalArgumentException("Query parameter를 Data Class로 변환하는데 실패했습니다.", cause)
    }
}