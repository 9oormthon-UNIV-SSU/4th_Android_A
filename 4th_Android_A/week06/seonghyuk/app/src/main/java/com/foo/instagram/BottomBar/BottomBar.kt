// 파일 경로: app/src/main/java/com/foo/instagram/BottomBar.kt
package com.foo.instagram

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController

/**
 * Bottom Navigation Bar
 */
@Composable
fun BottomBar(
    navController: NavController,
    currentRoute: String?
) {
    // 1) 탭 목록 정의
    val screens = listOf("home", "explore", "reels", "profile")

    // 2) 실제 Bar 그리기
    NavigationBar {
        screens.forEach { screen ->
            val isSelected = (currentRoute == screen)
            val iconRes = when (screen) {
                "home"    -> if (isSelected) R.drawable.ic_home_filled    else R.drawable.ic_home
                "explore" -> if (isSelected) R.drawable.ic_explore_filled  else R.drawable.ic_explore
                "reels"   -> if (isSelected) R.drawable.ic_reels_filled   else R.drawable.ic_reels
                "profile" -> if (isSelected) R.drawable.ic_profile_filled else R.drawable.ic_profile
                else      -> R.drawable.ic_home
            }

            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(iconRes),
                        contentDescription = null
                    )
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(screen) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = {
                    Text(text = screen.replaceFirstChar { it.uppercase() })
                }
            )
        }
    }
}