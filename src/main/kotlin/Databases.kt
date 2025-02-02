package com.example

import io.ktor.server.application.*
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val databaseConfig = DatabaseConfig.of(environment.config)

    val database =
        with(databaseConfig) {
            Database.connect(
                url = url,
                user = user,
                driver = driver,
                password = password,
            )
        }

    transaction(database) {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Users)
    }

    monitor.subscribe(ApplicationStopPreparing) {
        database.connector().close()
    }
}

data class DatabaseConfig(
    val url: String,
    val user: String,
    val driver: String,
    val password: String,
) {
    companion object {
        fun of(
            config: ApplicationConfig,
        ) =
            with(config) {
                val url = property("ktor.database.url").getString()
                val user = property("ktor.database.user").getString()
                val driver = property("ktor.database.driver").getString()
                val password = property("ktor.database.password").getString()

                DatabaseConfig(url, user, driver, password)
            }
    }
}