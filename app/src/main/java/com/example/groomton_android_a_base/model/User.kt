package com.example.groomton_android_a_base.model

data class User (
    val name: String,
    val id : String,
    val followers : Int,
    val followings : Int,
    val Posts : Int,
    val ProfilPictureUrl : String,
    val hasStory : Boolean,
    val hasUnseenStory : Boolean,
    val story: Story
    )