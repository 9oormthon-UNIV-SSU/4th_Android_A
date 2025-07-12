package com.example.groomton_android_a_base.viewmodel

import androidx.lifecycle.ViewModel
import com.example.groomton_android_a_base.R
import com.example.groomton_android_a_base.model.Post
import androidx.compose.runtime.mutableStateListOf




class FeedViewModel : ViewModel() {
    private val _postList = mutableStateListOf<Post>()
    val postList: List<Post> get() = _postList

    init {
        repeat(6) {
            _postList.add(
                Post(
                    id = it,
                    username = "user$it",
                    profileImage = R.drawable.ic_profile,
                    postImage = R.drawable.sample_image
                )
            )
        }
    }

    fun toggleLike(postId: Int) {
        val index = _postList.indexOfFirst { it.id == postId }
        if (index != -1) {
            _postList[index] = _postList[index].copy(
                isLiked = !_postList[index].isLiked
            )
        }
    }
}