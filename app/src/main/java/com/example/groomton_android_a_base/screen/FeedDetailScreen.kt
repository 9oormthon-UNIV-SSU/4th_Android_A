package com.example.groomton_android_a_base.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.groomton_android_a_base.R
import com.example.groomton_android_a_base.model.Feed
import com.example.groomton_android_a_base.ui.component.homescreen.BottomSheet
import com.example.groomton_android_a_base.viewmodel.ExploreFeedViewModel
import com.example.groomton_android_a_base.viewmodel.FeedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun feedDetailScreen(navController: NavController, exploreFeedId: String) {
    val feedViewModel: FeedViewModel = hiltViewModel()
    val exploreFeedViewModel: ExploreFeedViewModel = hiltViewModel()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedFeedForComments by remember { mutableStateOf<Feed?>(null) }
    val onCommentIconClick : (Feed) -> Unit = { feed ->
        selectedFeedForComments = feed
    }
    val dismissBottomSheet : () -> Unit = {
        selectedFeedForComments = null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("탐색 탭") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = "뒤로가기"
                        )
                    }
                }
            )
        }
    ) { innerpadding ->
        val exploreFeed = exploreFeedViewModel.exploreFeedList.find {
            it.feed.id == exploreFeedId
        }
        if (exploreFeed != null)
            FeedCard(exploreFeed.feed, feedViewModel, onCommentIconClick = onCommentIconClick, modifier = Modifier.padding(innerpadding))
        else Text("탐색 피드를 찾을 수 없습니다 : $exploreFeedId")
    }

    BottomSheet(feed = selectedFeedForComments, sheetState = sheetState, onDismiss = dismissBottomSheet)

}
