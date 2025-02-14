package com.example

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.util.*

/**
 * configuration에서 설정한 ObjectMapper를 다른 곳에서 사용하기 위해서 application attributes에 저장한다.
 *
 * application attributes는 application 전체에서 사용할 수 있는 key-value 저장소이다.
 *
 * Ktor Framework Library Source Code에서도 사용되는 기능/패턴이다.
 */
val objectMapperKey = AttributeKey<ObjectMapper>("ObjectMapper")

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

            /**
             * JavaTimeModule을 사용하려면 gradle에 jackson-datatype-jsr310 라이브러리를 추가해야 한다.
             *
             * JavaTimeModule은 Java 8에서 추가된 Date/Time API를 Jackson에서 사용할 수 있도록 해주는 모듈이다.
             *
             * JavaTimeModule을 사용하지 않는다면 LocalDate, LocalDateTime, ZonedDateTime 등의 Java 8 Date/Time API를 Client와 Server 간에 주고 받을 수 없다.
             */

            registerModules(JavaTimeModule())

            /**
             * jackson 함수 내부에 registerKotlinModule() 함수가 호출되고 있다.
             *
             * registerKotlinModule() 함수는 Kotlin Serialization을 사용하기 위한 함수이다.
             *
             * 이로 인해 Kotlin의 Value Class를 Client와 Server 간에 주고 받을 수 있다.
             */
            registerKotlinModule()

            /**
             * client가 보낸 JSON에 없는 property가 있을 때, Deserialization 과정에서 Exception을 발생시키지 않도록 설정한다.
             */
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

            /**
             * client가 보낸 JSON에 배열이 아닌 단일 값이 있을 때, Deserialization 시 배열로 변환한다.
             */
            configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)

            /**
             * 현재 jackson 함수 외에 상위에 함수들도 this keyword가 사용중이다. (총 3개)
             *
             * configureSerialization(), install(), jackson()에서 this가 사용되고 있다.
             *
             * 그래서 상위 함수들에 this를 호출하려면 명시적으로 this@함수명으로 호출해야 한다.
             *
             * 우리는 Application의 attributes에 ObjectMapper를 저장하기 위해 this@configureSerialization을 사용하였다.
             */
            this@configureSerialization.attributes.put(objectMapperKey, this)
        }
    }
}