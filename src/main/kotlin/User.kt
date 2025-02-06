package com.example

import java.time.LocalDate

object Users {
    val id = Unit
    val name = Unit
    val age = Unit
    val birthday = Unit
}

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