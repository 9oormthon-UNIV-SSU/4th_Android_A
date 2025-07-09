package com.example.groomton_android_a_base.ui.component

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.groomton_android_a_base.R


@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf("home", "explore", "reels", "profile")
    NavigationBar(
        modifier = Modifier.height(63.dp)
            .shadow(elevation = 4.dp),
        containerColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState() // navControeller에서 backstack 항목을 state 객체로 반환
        val currentRoute = navBackStackEntry?.destination?.route
        screens.forEach{ screen ->
            NavigationBarItem(
                icon = {
                    when(screen){
                        "home" -> Icon(painter = painterResource(R.drawable.ic_home), contentDescription = null)
                        "explore" -> Icon(painter = painterResource(R.drawable.ic_explore), contentDescription = null)
                        "reels" -> Icon(painter = painterResource(R.drawable.ic_reels), contentDescription = null)
                        "profile" -> Icon(painter = painterResource(R.drawable.ic_profile), contentDescription = null)
                    }

                },
                selected = currentRoute == screen,
                onClick = {
                    navController.navigate(screen){
                        popUpTo(navController.graph.startDestinationId) // stack 삭제
                        launchSingleTop = true // 이미 동일한 인스턴스가 맨 위에 있으면 재생성하지 않고 화면 유지
                        restoreState = true // popUpTo에서 저장해둔 상태 복원 -> 이전에 보던 위치, 입력값 복원 -> UX 향상
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}
