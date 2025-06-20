package com.example.week_06.dataclass

import java.sql.Timestamp

data class Story(
    val id : String,
    val user : User,
    val imageUrl : String,
//    val timestamp : Timestamp,
    val isSeen : Boolean
)
