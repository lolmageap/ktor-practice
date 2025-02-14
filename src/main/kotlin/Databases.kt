package com.example

import io.ktor.server.application.*
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {
    val dataSource = DataSource.of(environment.config)

    val database =
        with(dataSource) {
            Database.connect(
                url = url,
                user = user,
                driver = driver,
                password = password,
            )
        }

    /**
     * Application이 종료되는 것을 감지해서 종료되기 직전에 정의된 함수를 실행한다.
     *
     * 현재 Application이 종료되면 Database의 연결을 종료하는 코드를 작성했다.
     */
    monitor.subscribe(ApplicationStopPreparing) {
        database.connector().close()
    }
}

data class DataSource(
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
                DataSource(
                    url = property("ktor.database.url").getString(),
                    user = property("ktor.database.user").getString(),
                    driver = property("ktor.database.driver").getString(),
                    password = property("ktor.database.password").getString(),
                )
            }
    }
}