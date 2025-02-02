package com.example

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object Users : Table("users") {
    val id = long("id").autoIncrement()
    val name = varchar("name", 50).uniqueIndex()
    val age = integer("age")
    val birthday = date("birthday")

    override val primaryKey = PrimaryKey(id)
}

interface UserRepository {
    suspend fun create(
        username: Username,
        insertAge: Int,
        insertBirthday: LocalDate,
    )

    suspend fun findOne(
        name: Username,
        minAge: Int?,
        maxAge: Int?,
    ): UserResponse
}

class UserRepositoryImpl : UserRepository {
    override suspend fun create(
        username: Username,
        insertAge: Int,
        insertBirthday: LocalDate,
    ) {
        Users.insert {
            it[name] = username.value
            it[age] = insertAge
            it[birthday] = insertBirthday
        }
    }

    override suspend fun findOne(
        name: Username,
        minAge: Int?,
        maxAge: Int?,
    ) =
        Users.selectAll().where {
            (Users.name like "%${name.value}%") and Users.age.between(minAge, maxAge)
        }.first().let {
            UserResponse(
                id = it[Users.id],
                name = Username(it[Users.name]),
                age = it[Users.age],
                birthday = it[Users.birthday],
            )
        }
}