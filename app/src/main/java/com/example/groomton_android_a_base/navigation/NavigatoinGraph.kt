// 파일 경로: app/src/main/java/com/example/groomton_android_a_base/navigation/NavigationGraph.kt
package com.example.groomton_android_a_base.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.groomton_android_a_base.sampledata.SampleDataProvider
import com.example.groomton_android_a_base.ui.screen.ExploreScreen
import com.example.groomton_android_a_base.ui.screen.HomeScreen
import com.example.groomton_android_a_base.ui.screen.ProfileScreen
import com.example.groomton_android_a_base.ui.screen.ReelsScreen
import com.example.groomton_android_a_base.viewmodel.FeedViewModel
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.example.groomton_android_a_base.ui.screen.StoryDetailScreen // ❗ StoryDetailScreen import ❗


@Composable
fun NavigationGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    exploreQuery: String, // ❗ 파라미터 추가 ❗
    onExploreQueryChange: (String) -> Unit, // ❗ 파라미터 추가 ❗
    feedViewModel: FeedViewModel, // ❗ 파라미터 추가 ❗
    modifier: Modifier = Modifier
) {
    val feedViewModel: FeedViewModel = viewModel()
    var exploreQuery by rememberSaveable { mutableStateOf("") }

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(
                innerPadding = innerPadding,
                navController = navController,
                viewModel = feedViewModel
            )
        }
        composable("explore") {
            ExploreScreen(
                feeds = SampleDataProvider.sampleExploreFeeds,
                innerPadding = innerPadding,
                // ❗ query와 onQueryChange 파라미터 제거 ❗
                modifier = Modifier
            )
        }
        composable("reels") {
            ReelsScreen()
        }
        composable("profile/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            if (userId != null) {
                ProfileScreen(
                    userId = userId,
                    viewModel = feedViewModel,
                    navController = navController
                )
            }
        }
        composable("story_detail/{initialUserId}") { backStackEntry ->
            val initialUserId = backStackEntry.arguments?.getString("initialUserId")
            if (initialUserId != null) {
                StoryDetailScreen(
                    initialUserId = initialUserId,
                    viewModel = feedViewModel,
                    navController = navController
                )
            }
        }
    }
}