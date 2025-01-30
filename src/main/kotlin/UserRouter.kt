package com.example

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.user() {
    post("/api/v1/users") {
        val request = call.receive<UserRequest>()
        println("User request: $request")
        call.respondText { "요청이 성공적으로 들어왔습니다." }
    }

    get("/api/v1/users/{user-id}") {
        val userId = call.pathParameters["user-id"]?.toLong()
            ?: throw IllegalArgumentException("'user-id' is missing")

        val name = UserName(call.queryParameters["name"]!!)
        val minAge = call.queryParameters["minAge"]?.toInt()
        val maxAge = call.queryParameters["maxAge"]?.toInt()

        println("User ID: $userId, Name: $name, Min Age: $minAge, Max Age: $maxAge")
        call.respondText { "요청이 성공적으로 들어왔습니다." }
    }

    get("/api/v2/users/{user-id}") {
        val userId = call.pathParameters.userId

        val request = call.modelAttributes<GetUserRequest>()
        println("User ID: $userId, Request: $request")
        call.respondText { "요청이 성공적으로 들어왔습니다." }
    }
}

data class GetUserRequest(
    val name: UserName,
    val minAge: Int?,
    val maxAge: Int?,
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