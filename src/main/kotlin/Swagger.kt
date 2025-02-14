package com.example

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.routing.openApiSpec
import io.github.smiley4.ktorswaggerui.routing.swaggerUI
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSwagger() {
    install(SwaggerUI) {
        server {
            url = "http://localhost:8080"
        }

        routing {
            route("api.json") {
                openApiSpec()
            }

            route("/swagger") {
                swaggerUI("/api.json")
            }
        }
    }
}