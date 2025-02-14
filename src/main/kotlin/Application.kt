package com.example

import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

/**
 * Ktor Framework는 `module`이라는 명칭으로 설정 파일을 관리한다.
 *
 * Spring과 비교했을 때 @Configuration과 같은 역할을 한다.
 *
 * moduel은 Application 클래스의 확장 함수로 정의한다.
 *
 * application.conf file의 modules에 등록된 이름으로 호출된다.
 */
fun Application.module() {
    configureRouting()
    configureDatabases()
    configureFrameworks()
    configureMonitoring()
    configureSerialization()
    configureException()
}