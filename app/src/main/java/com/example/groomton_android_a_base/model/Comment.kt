package com.example.groomton_android_a_base.model

data class Comment(
    val id: String,
    val user: User,
    val content: String,
    val reply : List<Comment> = emptyList()
)
