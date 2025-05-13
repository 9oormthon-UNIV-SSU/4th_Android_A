package com.example.week_04

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    data class User(val id : String, val name : String)

    val userList = mutableStateListOf(
        User("1", "추교준"),
        User("2", "최지웅"),
        User("3", "김성혁"),
        User("4", "정재황"),
        User("5", "한금준"),
        User("6", "조강모")
    )

    fun getUserById(userId : String) : User {
        return userList.find {it.id == userId} ?: User("0", "Unknown")
    }

    fun updateUserName(userId: String, newName: String) {
        val index = userList.indexOfFirst { it.id == userId }
        if (index != -1) {
            val user = userList[index]
            userList[index] = user.copy(name = newName)
        }
    }
}