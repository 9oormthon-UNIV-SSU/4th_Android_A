package com.example.groomton_android_a_base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

// Compose-Navigation imports
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api

// BottomBar, Screen 들 import
import com.example.groomton_android_a_base.BottomBar
import com.example.groomton_android_a_base.ui.screen.HomeScreen
import com.example.groomton_android_a_base.ui.screen.ExploreScreen
import com.example.groomton_android_a_base.ui.screen.ReelsScreen
import com.example.groomton_android_a_base.ui.screen.ProfileScreen
import com.example.groomton_android_a_base.viewmodel.FeedViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.groomton_android_a_base.ui.theme.InstagramTheme
import com.example.groomton_android_a_base.sampledata.SampleDataProvider


class MainActivity : ComponentActivity() {
    // ❗ MainActivity의 Scaffold에 TopAppBar가 없으므로 @OptIn 제거 ❗
    // @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InstagramTheme {
                val navController = rememberNavController()
                val currentRoute by navController
                    .currentBackStackEntryAsState()
                    .let { state -> derivedStateOf { state.value?.destination?.route } }

                val feedViewModel: FeedViewModel = viewModel()
                var exploreQuery by rememberSaveable { mutableStateOf("") }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    // ❗ topBar 슬롯을 완전히 제거합니다. ❗
                    bottomBar = {
                        BottomBar(navController = navController, currentRoute = currentRoute)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            // HomeScreen에 innerPadding, navController, viewModel만 전달
                            HomeScreen(
                                innerPadding = innerPadding,
                                navController = navController,
                                viewModel = feedViewModel,
                                modifier = Modifier
                            )
                        }
                        composable("explore") {
                            ExploreScreen(
                                feeds = SampleDataProvider.sampleExploreFeeds,
                                innerPadding = innerPadding,
                                query = exploreQuery,
                                onQueryChange = { exploreQuery = it },
                                modifier = Modifier
                            )
                        }
                        composable("reels") {
                            ReelsScreen(innerPadding = innerPadding)
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
                    }
                }
            }
        }
    }
}
// ... (나머지 코드 유지) ...
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun Text(text: String, modifier: Modifier) {

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InstagramTheme {
        Greeting("Android")
    }
}