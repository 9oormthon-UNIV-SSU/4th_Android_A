package com.example.groomton_android_a_base.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groomton_android_a_base.Repository.SharedRepository
import com.example.groomton_android_a_base.model.Feed
import com.example.groomton_android_a_base.model.User
import com.example.groomton_android_a_base.sampledata.SampleDataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val sharedRepository: SharedRepository): ViewModel() {
    private val _feedList = mutableStateListOf<Feed>()
    val feedList: List<Feed> get() = _feedList

    init {
        // 초기 피드 데이터 로드
        _feedList.addAll(SampleDataProvider.allSampleFeeds.map { feed ->
            // 초기 피드 생성 시, SharedRepository의 현재 사용자 정보로 User 객체 설정
            val initialUser = sharedRepository.userList.find { it.id == feed.user.id } ?: feed.user
            feed.copy(user = initialUser)
        })

        // SharedRepository의 userList (SnapshotStateList) 변경을 감지하여 피드 업데이트
        // SnapshotStateList를 Flow로 변환하여 collect
        viewModelScope.launch {
            snapshotFlow { sharedRepository.userList.toList() } // toList()로 복사본을 Flow로 보내야 변경 감지가 용이
                .onEach { updatedUserList ->
                    updateFeedsWithNewUsers(updatedUserList)
                }
                .launchIn(this) // viewModelScope 내에서 Flow 수집
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

    private fun updateFeedsWithNewUsers(updatedUserList: List<User>) {
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