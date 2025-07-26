package com.example.groomton_android_a_base.model

import com.example.groomton_android_a_base.model.User

data class Comment(
    val id: String,
    val user: User,
    val text: String,
    val timestamp: Long
)