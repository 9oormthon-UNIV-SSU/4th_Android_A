package com.example.groomton_android_a_base.viewmodel

import androidx.lifecycle.ViewModel
import com.example.groomton_android_a_base.Repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExploreFeedViewModel @Inject constructor(private val sharedRepository: SharedRepository): ViewModel() {
    val exploreFeedList = sharedRepository.exploreFeedList

    init {
        // 초기 피드 데이터 로드
        exploreFeedList.map { feed ->
            // 초기 피드 생성 시, SharedRepository의 현재 사용자 정보로 User 객체 설정
            val initialUser = sharedRepository.userList.find { it.id == feed.user.id } ?: feed.user
            feed.copy(user = initialUser)
        }
    }

}

