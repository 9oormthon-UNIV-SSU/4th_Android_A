package com.example.groomton_android_a_base.Repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.groomton_android_a_base.model.ExploreFeed
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

    private val _exploreFeedList = SnapshotStateList<ExploreFeed>()
    val exploreFeedList : SnapshotStateList<ExploreFeed> = _exploreFeedList

    init {
        _userList.addAll(SampleDataProvider.sampleUsers.map {
            it.copy(hasUnseenStory = it.hasStory)
        })

        _feedList.addAll(SampleDataProvider.allSampleFeeds.map {
            feed ->
            val initialUser = _userList.find{it.id == feed.user.id} ?: feed.user
            feed.copy(user = initialUser)
        })

        _exploreFeedList.addAll(SampleDataProvider.sampleExploreFeeds.map {
            exploreFeed ->
            val initialUser = _userList.find{it.id == exploreFeed.user.id} ?: exploreFeed.user
            val updatedExploreFeed = exploreFeed.feed.copy(
                user = _userList.find { it.id == exploreFeed.feed.user.id } ?: exploreFeed.feed.user
            )
            exploreFeed.copy(
                user = initialUser, feed = updatedExploreFeed
            )
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
        //Feed 업데이트
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

        //ExploreFeed 업데이트
        val newExploreFeeds = _exploreFeedList.map { currentExploreFeed ->
            val updatedExploreFeedUser = updatedUserList.find { it.id == currentExploreFeed.user.id } ?: currentExploreFeed.user

            val updatedFeedUser = updatedUserList.find { it.id == currentExploreFeed.feed.user.id } ?: currentExploreFeed.feed.user
            val updatedInternalFeed = currentExploreFeed.feed.copy(user = updatedFeedUser)
            currentExploreFeed.copy(user = updatedExploreFeedUser, feed = updatedInternalFeed)
        }
        if(_exploreFeedList.toList() != newExploreFeeds){
            _exploreFeedList.clear()
            _exploreFeedList.addAll(newExploreFeeds)
        }
    }
}