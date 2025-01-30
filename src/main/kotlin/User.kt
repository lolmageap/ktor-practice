package com.example

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDate

object Users : Table("users") {
    val id = long("id").autoIncrement()
    val name = varchar("name", 50)
    val age = integer("age")
    val birthday = date("birthday")

    override val primaryKey = PrimaryKey(id)
}
class UserService(
    private val userRepository: UserRepository,
) {
    suspend fun create(
        username: UserName,
        insertAge: Int,
        insertBirthday: LocalDate,
    ) {
        newSuspendedTransaction {
            userRepository.create(
                username = username,
                insertAge = insertAge,
                insertBirthday = insertBirthday,
            )
        }
    }

    suspend fun readOne(
        name: UserName,
        minAge: Int?,
        maxAge: Int?,
    ) =
        newSuspendedTransaction {
            addLogger(StdOutSqlLogger)
            userRepository.findOne(
                name = name,
                minAge = minAge,
                maxAge = maxAge,
            ) ?: throw UserNotFoundException()
        }
}

interface UserRepository {
    suspend fun create(
        username: UserName,
        insertAge: Int,
        insertBirthday: LocalDate,
    )

    suspend fun findOne(
        name: UserName,
        minAge: Int?,
        maxAge: Int?,
    ): UserResponse?
}

class UserRepositoryImpl : UserRepository {
    override suspend fun create(
        username: UserName,
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
        name: UserName,
        minAge: Int?,
        maxAge: Int?,
    ) =
        Users.selectAll()
            .where {
                (Users.name like "%${name.value}%") and Users.age.between(minAge, maxAge)
            }
            .firstOrNull()
            ?.let {
                UserResponse(
                    id = it[Users.id],
                    name = UserName(it[Users.name]),
                    age = it[Users.age],
                    birthday = it[Users.birthday],
                )
            }
}