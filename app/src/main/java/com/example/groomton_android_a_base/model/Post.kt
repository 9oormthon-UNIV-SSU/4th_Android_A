package com.example.groomton_android_a_base.model

data class Post(
    val id: Int,
    val username: String,
    val profileImage: Int,
    val postImage: Int,
    var isLiked: Boolean = false,
    var comment: String = ""
)