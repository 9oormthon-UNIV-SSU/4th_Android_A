package com.example.groomton_android_a_base.model

import com.example.groomton_android_a_base.model.Comment
import com.example.groomton_android_a_base.model.User

data class Post(
    val id: Int,
    val user: User,
    val postImageUrl: String,
    var isLiked: Boolean = false,
    var comments: List<Comment> = emptyList(),
    val likesCount: Int = 0
)