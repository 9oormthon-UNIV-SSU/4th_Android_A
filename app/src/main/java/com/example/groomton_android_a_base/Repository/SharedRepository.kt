package com.example.groomton_android_a_base.Repository

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.groomton_android_a_base.model.User
import com.example.groomton_android_a_base.sampledata.SampleDataProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedRepository @Inject constructor() {
    private val _userList = SnapshotStateList<User>()
    val userList : SnapshotStateList<User> = _userList

    init {
        _userList.addAll(SampleDataProvider.sampleUsers.map {
            it.copy(hasUnseenStory = it.hasStory)
        })
    }

    fun toggleUserStoryState(userId: String){
        val userIndex = _userList.indexOfFirst {
            it.id == userId
        }
        if(userIndex != -1){
            val userToUpdate = _userList[userIndex]
            _userList[userIndex] = userToUpdate.copy(hasUnseenStory = false)

        }
    }
}