package com.example.groomton_android_a_base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
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
import com.example.groomton_android_a_base.ui.theme.InstagramTheme
import androidx.compose.runtime.derivedStateOf
// BottomBar, Screen 들 import

import com.example.groomton_android_a_base.BottomBar
import com.example.groomton_android_a_base.ui.screen.HomeScreen
import com.example.groomton_android_a_base.ui.screen.ExploreScreen
import com.example.groomton_android_a_base.ui.screen.ReelsScreen
import com.example.groomton_android_a_base.ui.screen.ProfileScreen
// 상단 바를 위한 추가 import
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.offset // offset import 추가
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.LazyListState // LazyColumn의 스크롤 상태를 위한 import (HomeScreen에서 사용)
import androidx.compose.material3.rememberTopAppBarState

import androidx.compose.material3.Divider // <<< Divider import


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
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

                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    // 3) BottomBar 호출: 함수 이름이 BottomBar 이지 .kt 가 아님
                    topBar = {
                        TopAppBar(
                            scrollBehavior = scrollBehavior, // <<<<<<<< 추가된 부분
                            modifier = Modifier.padding(vertical = 0.dp, horizontal = 0.dp),
                            title = {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = (0).dp), // 이 값을 조절하여 왼쪽으로 얼마나 더 밀지 결정

                                    horizontalArrangement = Arrangement.Start, // 로고는 왼쪽 정렬
                                    verticalAlignment = Alignment.CenterVertically // 세로 중앙 정렬
                                ) {
                                    // 기존 Box와 Icon은 로고의 경계선 확인용 (나중에 삭제)
                                    Box(
                                        modifier = Modifier
                                        // .border(3.dp, Color.Red) // 로고 Box의 경계선
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_logos_instagram),
                                            contentDescription = "Instagram Logo"
                                            ,modifier = Modifier.padding(start = 0.dp) // 로고 자체의 왼쪽 패딩도 0으로 해도 안됨

                                        )
                                    }
                                    //  로고 아래에 선을 추가하는 부분
                                }
                            },
                            actions={
                                IconButton(onClick={/*좋아요 눌렀을 때 뭐할건지는 ;;*/})
                                {
                                    Icon(
                                        painter = painterResource(id=R.drawable.ic_heart_outline),
                                        contentDescription = "Likes"
                                    )
                                }
                                IconButton(onClick={/*좋아요 눌렀을 때 뭐할건지는 ;;*/})
                                {
                                    Icon(
                                        painter = painterResource(id=R.drawable.ic_message_icon),
                                        contentDescription = "Messages"
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.background // Scaffold의 배경색과 맞춤
                            )
                        )
                    },
                    bottomBar = {
                        BottomBar(navController = navController, currentRoute = currentRoute)
                    }
                ) { innerPadding -> // Scaffold에서 innerPadding을 받음
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        // NavHost의 padding은 innerPadding을 직접 사용합니다.
                        modifier = Modifier.padding(innerPadding)
                        // .offset(y = (-30).dp) //
                    ) {
                        composable("home") {
                            // HomeScreen에 innerPadding과 scrollBehavior를 전달
                            HomeScreen(
                                innerPadding = innerPadding, // <<< innerPadding 전달
                                scrollBehavior = scrollBehavior // <<< scrollBehavior 전달
                            )
                        }
                        composable("explore") { ExploreScreen() }
                        composable("reels") { ReelsScreen() }
                        composable("profile") { ProfileScreen() }
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