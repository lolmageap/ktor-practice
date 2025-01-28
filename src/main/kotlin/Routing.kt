package com.example

import io.ktor.server.application.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureRouting() {
    install(DoubleReceive)
    install(RequestValidation) {
        validate<String> { bodyText ->
            if (!bodyText.startsWith("Hello"))
                ValidationResult.Invalid("Body text should start with 'Hello'")
            else ValidationResult.Valid
        }
    }
}
