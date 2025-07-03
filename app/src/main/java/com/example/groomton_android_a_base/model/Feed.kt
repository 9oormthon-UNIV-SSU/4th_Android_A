package com.example.groomton_android_a_base.model

data class Feed(
    val id : String,
    val user : User,
    val imageUrl : String,
    var likeCount : Int,
    val caption : String,
    var commentCount : Int,
    var isBookmarked : Boolean,
    var isLiked : Boolean = false,
    var comments : List<Comment>
)