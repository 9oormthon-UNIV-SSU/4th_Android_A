package com.example.groomton_android_a_base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.groomton_android_a_base.ui.theme.GroomTon_Android_A_BaseTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.groomton_android_a_base.screen.ExploreScreen
import com.example.groomton_android_a_base.screen.HomeScreen
import com.example.groomton_android_a_base.screen.ProfileScreen
import com.example.groomton_android_a_base.screen.ReelsScreen
import com.example.groomton_android_a_base.ui.component.BottomBar
import com.example.groomton_android_a_base.sampledata.SampleDataProvider.sampleExploreFeeds
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GroomTon_Android_A_BaseTheme {
                NavigationBar(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun NavigationBar(modifier: Modifier = Modifier){
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
        bottomBar = { BottomBar(navController) },
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home"){HomeScreen()}
                composable("explore"){ExploreScreen(feeds = sampleExploreFeeds)}
                composable("reels"){ReelsScreen()}
                composable("profile"){ProfileScreen()}
            }
        }
    )
}

@Preview
@Composable
fun NavigationBarPreview() {
    GroomTon_Android_A_BaseTheme {
        NavigationBar(modifier = Modifier.fillMaxSize())
    }
}