package com.example.groomton_android_a_base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue // For `by` delegation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

// Compose-Navigation imports
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState // For current back stack entry
import androidx.compose.runtime.derivedStateOf // For deriving state (though simplified below)

import androidx.compose.runtime.mutableStateOf // For `rememberSaveable { mutableStateOf(...) }`
import androidx.compose.runtime.setValue // For `by` delegation
import androidx.compose.foundation.layout.PaddingValues // For innerPadding
import androidx.compose.material3.ExperimentalMaterial3Api // @OptIn for ExperimentalMaterial3Api components
import androidx.compose.runtime.saveable.rememberSaveable // For `rememberSaveable`

// BottomBar, Screen imports
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
    // @OptIn(ExperimentalMaterial3Api::class) // Only needed if directly using M3 experimental APIs in MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InstagramTheme {
                val navController = rememberNavController()

                // ❗ currentRoute derivation simplification and fix ❗
                val currentRoute: String? = navController.currentBackStackEntryAsState().value?.destination?.route


                val feedViewModel: FeedViewModel = viewModel()
                // exploreQuery는 ExploreScreen 내부에서 관리하므로 여기서 필요 없습니다.
                // var exploreQuery by rememberSaveable { mutableStateOf("") } // ❗ 제거 ❗

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
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
                            // HomeScreen에 innerPadding, navController, viewModel 전달
                            HomeScreen(
                                innerPadding = innerPadding,
                                navController = navController,
                                viewModel = feedViewModel,
                                modifier = Modifier
                            )
                        }
                        composable("explore") {
                            // ExploreScreen은 이제 query와 onQueryChange를 자체적으로 관리합니다.
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
                    }
                }
            }
        }
    }
}
// ... (나머지 코드 유지) ...