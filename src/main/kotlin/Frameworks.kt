package com.example

import io.ktor.server.application.*
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()
        modules(userModule)
    }
}

val userModule = module {
    /**
     * single bean: 애플리케이션 전체에서 하나의 인스턴스만 생성한다.
     *
     * single 함수에 기본 아규먼트를 가지고 있다면 primary bean으로 등록된다.
     *
     * 만약 기본 아규먼트를 가지고 있는 bean이 없다면 마지막에 선언된 bean이 primary bean으로 등록된다.
     */
    single<UserRepository>() { UserRepositoryImplV1() }

    /**
     * qualified bean: 같은 타입의 bean이 여러 개 존재할 때, 특정 bean을 선택할 수 있다.
     *
     * 아래와 같이 named 함수를 사용하여 특정 bean을 선택할 수 있다.
     */
    single<UserRepository>(named("first")) { UserRepositoryImplV1() }
    single<UserRepository>(named("second")) { UserRepositoryImplV2() }

    /**
     * factory bean: 매번 새로운 인스턴스를 생성한다.
     */
//    factory<UserRepository>() { UserRepositoryImpl() }
}