package com.example.groomton_android_a_base.dataclass

data class Story(
    val id : String,
    val user : User,
    val imageUrl : String,
//    val timestamp : Timestamp,
    val isSeen : Boolean
)
