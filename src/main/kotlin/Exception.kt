package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

/**
 * Ktor Framework는 `install`함수로 플러그인을 추가할 수 있다.
 *
 * StatusPages는 예외 처리를 위한 플러그인이다.
 *
 * 이렇게 추가한 플러그인은 Spring의 @Bean과 같은 역할을 한다.
 *
 * Spring과 다른 점은 Spring은 Dependency를 implement해서 사용할 시 @AutoConfigure를 사용해서 자동으로 설정을 해주지만,
 * Ktor는 직접 플러그인을 추가해야 한다.
 *
 * 그렇기 때문에 편의성은 떨어질 수 있지만 사용하지 않는 Dependency가 런타임에 영향을 미치지 않는다.
 */
fun Application.configureException() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            cause.printStackTrace()
            call.respondText(status = HttpStatusCode.InternalServerError) { "서버에서 에러가 발생했습니다." }
        }
    }
}