package com.example.groomton_android_a_base.model

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.groomton_android_a_base.sampledata.SampleDataProvider.sampleExploreFeeds
import com.example.groomton_android_a_base.screen.ExploreScreen
import com.example.groomton_android_a_base.screen.HomeScreen
import com.example.groomton_android_a_base.screen.ProfileScreen
import com.example.groomton_android_a_base.screen.ReelsScreen
import com.example.groomton_android_a_base.screen.feedDetailScreen

@Composable
fun AppNavHost(navController: NavHostController, startDestination: String,
               modifier: Modifier) {
    NavHost(navController = navController, startDestination = startDestination){
        composable("home"){ HomeScreen() }
        composable("explore"){ ExploreScreen(navController = navController, feeds = sampleExploreFeeds) }
        composable("reels"){ ReelsScreen() }
        composable("profile"){ ProfileScreen() }
        composable(route = "feed_detail/{feedId}", arguments = listOf(navArgument("feedId"){ type = NavType.StringType })){
            backStackEntry ->
            val feedId = backStackEntry.arguments?.getString("feedId")
            if (feedId != null) {
                val exploreFeed = sampleExploreFeeds.find {
                    it.feed.id == feedId
                }
                if (exploreFeed != null){
                    feedDetailScreen(navController = navController,exploreFeedId = feedId)
                }
                else Text("피드를 찾을 수 없습니다 : $feedId")
            }
            else Text("피드 아이디가 없습니다 : $feedId")
        }
    }
}