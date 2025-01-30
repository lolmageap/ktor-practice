package com.example

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object Users: Table("users") {
    val id = long("id").autoIncrement()
    val name = varchar("name", 50)
    val age = integer("age")
    val birthday = date("birthday")

    override val primaryKey = PrimaryKey(id)
}