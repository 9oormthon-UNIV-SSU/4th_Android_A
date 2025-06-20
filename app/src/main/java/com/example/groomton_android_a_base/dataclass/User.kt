package com.example.week_06.dataclass

data class User (
    val name: String,
    val id : String,
    val followers : Int,
    val followings : Int,
    val Posts : Int,
    val ProfilPictureUrl : String,
    val hasUnseenStory : Boolean
    )