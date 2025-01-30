package com.example

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.time.LocalDate

fun Route.user() {
    val userService: UserService by application.inject()

    post("/api/v1/users") {
        val request = call.receive<UserRequest>()

        userService.create(
            username = request.name,
            insertAge = request.age,
            insertBirthday = request.birthday,
        )

        call.respondText(status = HttpStatusCode.Created) { "요청이 성공적으로 들어왔습니다." }
    }

    get("/api/v1/users/{user-id}") {
        val userId = call.pathParameters["user-id"]?.toLong()
            ?: throw IllegalArgumentException("'user-id' is missing")

        val name = UserName(call.queryParameters["name"]!!)
        val minAge = call.queryParameters["minAge"]?.toInt()
        val maxAge = call.queryParameters["maxAge"]?.toInt()

        val response = userService.readOne(
            name = name,
            minAge = minAge,
            maxAge = maxAge,
        )

        call.respond(response)
    }

    get("/api/v2/users/{user-id}") {
        val userId = call.pathParameters.userId

        val request = call.modelAttributes<GetUserRequest>()

        val response = userService.readOne(
            name = request.name,
            minAge = request.minAge,
            maxAge = request.maxAge,
        )

        call.respond(response)
    }
}

data class GetUserRequest(
    val name: UserName,
    val minAge: Int?,
    val maxAge: Int?,
)

data class UserResponse(
    val id: Long,
    val name: UserName,
    val age: Int,
    val birthday: LocalDate,
)

val Parameters.userId: Long
    get() = this["user-id"]?.toLong()
        ?: throw IllegalArgumentException("'user-id' is missing")

inline fun <reified T : Any> RoutingCall.modelAttributes(): T {
    require(T::class.isData) { "Client에게 값을 받을 때는 Data Class만 지원합니다." }

    val map =
        queryParameters.entries().associate { (key, value) ->
            if (value.size == 1) key to value.first()
            else key to value
        }

    val mapper = application.attributes[ObjectMapperKey]

    return try {
        mapper.convertValue(map, T::class.java)
    } catch (cause: Exception) {
        throw IllegalArgumentException("Query parameter를 Data Class로 변환하는데 실패했습니다.", cause)
    }
}