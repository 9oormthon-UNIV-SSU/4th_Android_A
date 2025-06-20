package com.example.week_06.dataclass

import java.sql.Timestamp

data class Comment(
    val id: String,
    val user: User,
    val content: String

)
