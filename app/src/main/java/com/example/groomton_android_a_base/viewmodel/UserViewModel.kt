package com.example.groomton_android_a_base.viewmodel

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.groomton_android_a_base.Repository.SharedRepository
import com.example.groomton_android_a_base.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val sharedRepository: SharedRepository) : ViewModel() {
    val userList: SnapshotStateList<User> = sharedRepository.userList

    fun toggleUserStoryState(userId: String){
        sharedRepository.toggleUserStoryState(userId)
    }
}