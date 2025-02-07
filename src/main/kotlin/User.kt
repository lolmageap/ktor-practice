package com.example

import java.time.LocalDate

interface UserRepository {
    suspend fun create(
        name: UserName,
        age: Int,
        birthday: LocalDate,
    )

    suspend fun findOne(
        name: UserName,
        minAge: Int,
        maxAge: Int,
    ): UserResponse
}