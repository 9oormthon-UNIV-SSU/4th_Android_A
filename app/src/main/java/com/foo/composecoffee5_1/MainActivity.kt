package com.foo.composecoffee5_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.foo.composecoffee5_1.ui.theme.Composecoffee5_1Theme

sealed class Screen(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Search : Screen("search", "Search", Icons.Filled.Search)
    object Profile : Screen("profile", "Profile", Icons.Filled.AccountCircle)
}

class MainViewModel : ViewModel() {
    var currentScreen by mutableStateOf<Screen>(Screen.Home) // 일단 초기상태는 home
        private set

    fun setScreen(screen: Screen) {
        currentScreen = screen
    }

    fun onFabClick(): String {
        return when (currentScreen) {
            is Screen.Home -> "Home FAB Clicked"
            is Screen.Search -> "Search FAB Clicked"
            is Screen.Profile -> "Profile FAB Clicked"
        }
    }
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Composecoffee5_1Theme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = viewModel()

                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text(viewModel.currentScreen.label) }
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            listOf(Screen.Home, Screen.Search, Screen.Profile).forEach { screen ->
                                NavigationBarItem(
                                    selected = viewModel.currentScreen.route == screen.route,
                                    onClick = {
                                        viewModel.setScreen(screen) //누른애가 screen인자로서 뷰모델 setScreen 전달
                                        navController.navigate(screen.route) { //여기서 navhost 작동
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = { Icon(screen.icon, contentDescription = screen.label) },
                                    label = { Text(screen.label) }
                                )
                            }
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            println(viewModel.onFabClick())
                        }) {
                            Text("+")
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) { HomeScreen() }
                        composable(Screen.Search.route) { SearchScreen() }
                        composable(Screen.Profile.route) { ProfileScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Text("Home Screen", modifier = Modifier.padding(16.dp))
}

@Composable
fun SearchScreen() {
    Text("Search Screen", modifier = Modifier.padding(16.dp))
}

@Composable
fun ProfileScreen() {
    Text("Profile Screen", modifier = Modifier.padding(16.dp))
}
