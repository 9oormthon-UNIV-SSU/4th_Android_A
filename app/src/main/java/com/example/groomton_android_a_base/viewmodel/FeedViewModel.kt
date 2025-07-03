package com.example.groomton_android_a_base.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.groomton_android_a_base.model.Feed
import com.example.groomton_android_a_base.sampledata.SampleDataProvider

class FeedViewModel : ViewModel() {
    private val _feedList = mutableStateListOf<Feed>()
    val feedList: List<Feed> get() = _feedList

    init {
        _feedList.addAll(SampleDataProvider.allSampleFeeds)
    }

    fun toggleLike(feedId : String){
        val index = _feedList.indexOfFirst { it.id == feedId }

        if (index != -1){
            val currentFeed = _feedList[index]
            val newLikeState = !currentFeed.isLiked
            val newLikeCount = if (currentFeed.isLiked) currentFeed.likeCount - 1 else currentFeed.likeCount + 1
            _feedList[index] = currentFeed.copy(isLiked = newLikeState, likeCount = newLikeCount)
        }
    }

    fun toggleBookmark(feedId: String){
        val index = _feedList.indexOfFirst { it.id == feedId }

        if (index != -1){
            val currentFeed = _feedList[index]
            val newBookmarkState = !currentFeed.isBookmarked
            _feedList[index] = currentFeed.copy(isBookmarked = newBookmarkState)
        }
    }

}