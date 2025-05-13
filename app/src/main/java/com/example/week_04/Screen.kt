package com.example.week_04

sealed class Screen(val route : String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{userId}"){
        fun createRoute(userId : String) = "detail/$userId"
    }
}