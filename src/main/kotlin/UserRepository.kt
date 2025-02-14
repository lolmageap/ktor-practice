package com.example

import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.selectAll
import java.time.LocalDate

interface UserRepository {
    suspend fun create(
        name: UserName,
        age: Int,
        birthday: LocalDate,
    )

    suspend fun findAll(
        name: UserName,
        minAge: Int,
        maxAge: Int,
        pageRequest: PageRequest,
    ): List<UserResponse>
}

class UserRepositoryImplV1 : UserRepository {
    override suspend fun create(
        name: UserName,
        age: Int,
        birthday: LocalDate,
    ) {
        Users.insert {
            it[Users.name] = name.value
            it[Users.age] = age
            it[Users.birthday] = birthday
        }
    }

    override suspend fun findAll(
        name: UserName,
        minAge: Int,
        maxAge: Int,
        pageRequest: PageRequest
    ): List<UserResponse> {
        return Users.selectAll()
            .where {
                Users.name.lowerCase() like "%${name.value.lowercase()}%"
            }
            .andWhere {
                Users.age.between(minAge, maxAge)
            }
            .limit(pageRequest.size)
            .offset(pageRequest.offset)
            .orderBy(
                *pageRequest.sorts.map {
                    Users.findColumn(it.property) to it.direction
                }.toTypedArray()
            )
            .map {
                UserResponse(
                    id = it[Users.id],
                    name = UserName(it[Users.name]),
                    age = it[Users.age],
                    birthday = it[Users.birthday],
                    status = it[Users.status],
                )
            }
    }
}

class UserRepositoryImplV2 : UserRepository {
    override suspend fun create(
        name: UserName,
        age: Int,
        birthday: LocalDate,
    ) {
        println("create v2")
        Users.insert {
            it[Users.name] = name.value
            it[Users.age] = age
            it[Users.birthday] = birthday
        }
    }

    override suspend fun findAll(
        name: UserName,
        minAge: Int,
        maxAge: Int,
        pageRequest: PageRequest
    ): List<UserResponse> {
        println("findAll v2")
        return Users.selectAll()
            .where {
                Users.name.lowerCase() like "%${name.value.lowercase()}%"
            }
            .andWhere {
                Users.age.between(minAge, maxAge)
            }
            .limit(pageRequest.size)
            .offset(pageRequest.offset)
            .orderBy(
                *pageRequest.sorts.map {
                    Users.findColumn(it.property) to it.direction
                }.toTypedArray()
            )
            .map {
                UserResponse(
                    id = it[Users.id],
                    name = UserName(it[Users.name]),
                    age = it[Users.age],
                    birthday = it[Users.birthday],
                    status = it[Users.status],
                )
            }
    }
}