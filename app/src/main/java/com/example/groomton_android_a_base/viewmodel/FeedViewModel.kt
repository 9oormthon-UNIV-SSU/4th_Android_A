package com.example.groomton_android_a_base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import com.example.groomton_android_a_base.model.Comment
import com.example.groomton_android_a_base.model.Post
import com.example.groomton_android_a_base.sampledata.SampleDataProvider
import kotlin.random.Random
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.groomton_android_a_base.model.User


class FeedViewModel : ViewModel() {
    private val _postList = mutableStateListOf<Post>()
    val postList: List<Post> get() = _postList

    private val _currentPost = MutableStateFlow<Post?>(null)
    val currentPost: StateFlow<Post?> = _currentPost

    init {
        repeat(80) { index ->
            _postList.add(SampleDataProvider.createSamplePost(index))
        }
    }

    fun getPostById(postId: Int) {
        _currentPost.value = _postList.firstOrNull { it.id == postId }
    }

    fun toggleLike(postId: Int) {
        val index = _postList.indexOfFirst { it.id == postId }
        if (index != -1) {
            val oldPost = _postList[index]
            val newPost = oldPost.copy(
                isLiked = !oldPost.isLiked,
                likesCount = if (oldPost.isLiked) oldPost.likesCount - 1 else oldPost.likesCount + 1
            )
            _postList[index] = newPost
            if (_currentPost.value?.id == postId) {
                _currentPost.value = newPost
            }
        }
    }

    fun addComment(postId: Int, newCommentText: String) {
        val index = _postList.indexOfFirst { it.id == postId }
        if (index != -1) {
            val oldPost = _postList[index]
            val currentUser = SampleDataProvider.sampleUsers.first { it.id == "my_user" }
            val newComment = Comment(
                id = "c${System.currentTimeMillis()}",
                user = currentUser,
                text = newCommentText,
                timestamp = System.currentTimeMillis()
            )
            val updatedPost = oldPost.copy(
                comments = oldPost.comments + newComment
            )
            _postList[index] = updatedPost
            if (_currentPost.value?.id == postId) {
                _currentPost.value = updatedPost
            }
        }
    }
}