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
import com.example.groomton_android_a_base.navigation.NavigationGraph

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
                    bottomBar = {
                        BottomBar(navController = navController, currentRoute = currentRoute)
                    }
                ) { innerPadding ->
                    // ❗ NavHost를 직접 정의하는 대신 NavigationGraph를 호출합니다. ❗
                    NavigationGraph(
                        navController = navController,
                        innerPadding = innerPadding,
                        exploreQuery = exploreQuery, // NavigationGraph로 전달
                        onExploreQueryChange = { exploreQuery = it }, // NavigationGraph로 전달
                        feedViewModel = feedViewModel, // NavigationGraph로 전달
                        modifier = Modifier // Modifier 전달
                    )
                }
            }
        }
    }
}
// ... (나머지 코드 유지) ...