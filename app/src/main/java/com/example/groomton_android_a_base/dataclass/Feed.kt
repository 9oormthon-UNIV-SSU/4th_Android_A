package com.example.groomton_android_a_base.dataclass

data class Feed(
    val id : String,
    val user : User,
    val imageUrl : String,
    val likeCount : Int,
    val caption : String,
    val commentCount : Int,
    val isBookmarked : Boolean,
    val isLiked : Boolean,
    val comments : List<Comment>
)