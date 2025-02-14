package com.example

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import kotlin.reflect.full.memberProperties

object Users : BaseTable("user") {
    val name = varchar("name", 50).uniqueIndex()
    val age = integer("age")
    val birthday = date("birthday")
    val status = enumerationByName("status", 50, UserStatus::class).default(UserStatus.ACTIVE)
}

abstract class BaseTable(name: String) : Table(name) {
    val id = long("id").autoIncrement()

    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    val updatedAt = datetime("updated_at").clientDefault { LocalDateTime.now() }

    override val primaryKey = PrimaryKey(id)

    /**
     * Column의 이름을 변수명과 비교해서 일치하는 Column을 찾는다.
     */
    fun findColumn(property: String) =
        this::class.memberProperties.find { it.name == property }
            ?.let { it.getter.call(this) as Column<*> }
            ?: throw IllegalArgumentException("${property}는 존재하지 않는 컬럼입니다.")
}