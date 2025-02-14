package com.example

import io.github.smiley4.ktorswaggerui.dsl.routing.get
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    routing {
        val userRepositoryV1 by application.inject<UserRepository>()
        val userRepositoryV2 by application.inject<UserRepository>(named("second"))

        get("/") {
            call.respondText { "Hello, Ktor!!" }
        }

        /**
         * route 함수를 사용하여 `Path`를 그룹화 할 수 있다.
         *
         * spring의 @RequestMapping과 비슷하다.
         */
        route("/api/v1") {
            /**
             * ApplicationCall의 extension 함수인 receive를 사용하여 `Request Body`를 읽어온다.
             */
            post("/users", {
                summary = "회원 등록"

                request {
                    body<CreateUserRequest>()
                }

                response {
                    HttpStatusCode.Created to {
                        description = "성공"
                    }
                }
            }) {
                val request = call.receive<CreateUserRequest>()

                newSuspendedTransaction {
                    addLogger(StdOutSqlLogger)
                    userRepositoryV1.create(
                        name = request.name,
                        age = request.age,
                        birthday = request.birthday,
                    )
                }

                call.respondText(status = HttpStatusCode.Created) { "성공" }
            }

            /**
             * RoutingCall의 extension 함수인 pathParameters를 사용하여 `Path Parameter`를 읽어온다.
             *
             * 그리고 queryParameters를 사용하여 `Query Parameter`를 읽어온다.
             *
             * ktor는 queryParameter를 Model로 읽어오는 기능을 제공하지 않는다.
             *
             * 그래서 Model로 읽어오려면 직접 구현해야 한다.
             */
            get("/users/{user-name}") {
                val userName = UserName(call.pathParameters["user-name"]!!)

                val minAge = call.queryParameters["minAge"]!!.toInt()
                val maxAge = call.queryParameters["maxAge"]!!.toInt()

                val page = call.queryParameters["page"]!!.toInt()
                val size = call.queryParameters["size"]!!.toInt()
                val properties = call.queryParameters.getAll("properties")!!
                val directions = call.queryParameters.getAll("directions")!!.map(SortOrder::valueOf)

                val findUserRequest =
                    FindUserRequest(
                        minAge = minAge,
                        maxAge = maxAge,
                    )

                val pageRequest =
                    PageRequest(
                        page = page,
                        size = size,
                        properties = properties,
                        directions = directions,
                    )

                val response =
                    newSuspendedTransaction {
                        addLogger(StdOutSqlLogger)
                        userRepositoryV1.findAll(
                            name = userName,
                            minAge = findUserRequest.minAge,
                            maxAge = findUserRequest.maxAge,
                            pageRequest = pageRequest,
                        )
                    }

                call.respond(response)
            }
        }

        route("/api/v2") {
            get("/users/{user-name}", {
                summary = "회원 조회"
                request {
                    pathParameter<String>("user-name")

                    queryParameter<Int>("minAge")
                    queryParameter<Int>("maxAge")

                    queryParameter<Int>("page")
                    queryParameter<Int>("size")
                    queryParameter<List<String>>("properties")
                    queryParameter<List<SortOrder>>("directions")
                }

                response {
                    HttpStatusCode.OK to {
                        body<List<UserResponse>>()
                    }
                }
            }) {
                val userName = call.parameters.userName

                val findUserRequest = call.modelAttribute<FindUserRequest>()
                val pageRequest = call.modelAttribute<PageRequest>()

                val response =
                    newSuspendedTransaction {
                        addLogger(StdOutSqlLogger)
                        userRepositoryV1.findAll(
                            name = userName,
                            minAge = findUserRequest.minAge,
                            maxAge = findUserRequest.maxAge,
                            pageRequest = pageRequest,
                        )
                    }

                call.respond(response)
            }
        }
    }
}

val Parameters.userName
    get() = UserName(this["user-name"]!!)

inline fun <reified T> RoutingCall.modelAttribute(): T {
    val map =
        queryParameters.entries().associate { (key, values) ->
            if (values.isEmpty()) key to null
            else if (values.size == 1) key to values.first()
            else key to values
        }

    val objectMapper = application.attributes[objectMapperKey]

    return objectMapper.convertValue(map, T::class.java)
}
