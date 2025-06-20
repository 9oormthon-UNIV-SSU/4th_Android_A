package com.foo.instagram

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
import androidx.compose.runtime.getValue
import com.foo.instagram.ui.theme.InstagramTheme
import androidx.compose.runtime.derivedStateOf
// BottomBar, Screen 들 import

import com.foo.instagram.BottomBar
import com.foo.instagram.screen.HomeScreen
import com.foo.instagram.screen.ExploreScreen
import com.foo.instagram.screen.ReelsScreen
import com.foo.instagram.screen.ProfileScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InstagramTheme {
                // 1) navController 생성
                val navController = rememberNavController()
                // 2) 현재 route 관찰
                val currentRoute by navController
                    .currentBackStackEntryAsState()
                    .let { state -> derivedStateOf { state.value?.destination?.route } }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    // 3) BottomBar 호출: 함수 이름이 BottomBar 이지 .kt 가 아님
                    bottomBar = {
                        BottomBar(navController = navController, currentRoute = currentRoute)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home")    { HomeScreen() }
                        composable("explore") { ExploreScreen() }
                        composable("reels")   { ReelsScreen() }
                        composable("profile"){ ProfileScreen() }
                    }
                }
            }
        }
    }
}
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