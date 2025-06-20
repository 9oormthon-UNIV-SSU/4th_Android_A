package com.example.week_06.ui.component

import com.example.week_06.dataclass.User
import java.sql.Timestamp

data class Comment(
    val id : String,
    val user : User,
    val text : String,
    val timestamp : Timestamp
)