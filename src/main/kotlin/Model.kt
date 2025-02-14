package com.example

import org.jetbrains.exposed.sql.SortOrder
import java.time.LocalDate

/**
 * `@JvmInline`은 컴파일 타임에 wrapper class를 primitive type으로 인라이닝(변경)해준다.
 *
 * 즉, `UserName`은 컴파일 타임에 `String`타입으로 변경된다.
 */
@JvmInline
value class UserName(val value: String) {
    init {
        require(value.length in 3..20)
    }
}

enum class UserStatus {
    ACTIVE, INACTIVE,
}

data class CreateUserRequest(
    val name: UserName,
    val age: Int,
    val birthday: LocalDate,
)

data class FindUserRequest(
    val minAge: Int,
    val maxAge: Int,
)

data class UserResponse(
    val id: Long,
    val name: UserName,
    val age: Int,
    val birthday: LocalDate,
    val status: UserStatus,
)

data class Sort(
    val property: String,
    val direction: SortOrder,
)

data class PageRequest(
    val page: Int,
    val size: Int,
    private val properties: List<String> = emptyList(),
    private val directions: List<SortOrder> = emptyList(),
) {
    init {
        require(properties.size == directions.size)
    }

    val offset
        get() = (page * size).toLong()

    val sorts =
        properties.zip(directions).map {
            Sort(it.first, it.second)
        }
}