package com.example

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.util.*
import java.time.LocalDate

val ObjectMapperKey = AttributeKey<ObjectMapper>("ObjectMapper")

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            registerModules(JavaTimeModule())
            this@configureSerialization.attributes.put(ObjectMapperKey, this)
        }
    }
}

data class UserRequest(
    val name: UserName,
    val age: Int,
    val birthday: LocalDate,
)

@JvmInline
value class UserName(
    private val value: String,
) {
    init {
        require(value.length in 1..20) {
            "Name must be between 1 and 10 characters"
        }
    }
}