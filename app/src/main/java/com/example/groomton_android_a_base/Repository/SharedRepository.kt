package com.example.groomton_android_a_base.Repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.groomton_android_a_base.model.Feed
import com.example.groomton_android_a_base.model.User
import com.example.groomton_android_a_base.sampledata.SampleDataProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedRepository @Inject constructor() {
    private val _userList = SnapshotStateList<User>()
    val userList : SnapshotStateList<User> = _userList

    private val _feedList = SnapshotStateList<Feed>()
    val feedList : SnapshotStateList<Feed> = _feedList

    init {
        _userList.addAll(SampleDataProvider.sampleUsers.map {
            it.copy(hasUnseenStory = it.hasStory)
        })

        _feedList.addAll(SampleDataProvider.allSampleFeeds.map {
            feed ->
            val initialUser = _userList.find{it.id == feed.user.id} ?: feed.user
            feed.copy(user = initialUser)
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

    fun updateFeedsWithNewUsers(updatedUserList: List<User>) {
        val newFeeds = _feedList.map { currentFeed ->
            updatedUserList.find { it.id == currentFeed.user.id }?.let { freshUser ->
                currentFeed.copy(user = freshUser)
            } ?: currentFeed
        }

        val currentFeedsSet = _feedList.toSet() //Set은 중복을 허용 안하고, 순서가 중요하지 않음 -> 같은지 비교할 때 유용
        val newFeedsSet = newFeeds.toSet()

        if (currentFeedsSet != newFeedsSet) {
            _feedList.clear()
            _feedList.addAll(newFeeds)
        }
    }
}