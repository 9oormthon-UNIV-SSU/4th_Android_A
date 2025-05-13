package com.foo.composecoffee4_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.foo.composecoffee4_3.ui.theme.Composecoffee4_3Theme

// --- 데이터 클래스 ---
data class User(val id: String, val name: String)

// --- ViewModel ---
class MainViewModel : ViewModel() {
    var userList = mutableStateListOf(
        User("1", "성혁"),
        User("2", "교준"),
        User("3", "재훈햄")
    )
        private set

    fun getUserById(id: String): User? = userList.find { it.id == id }

    fun updateUserName(id: String, newName: String) {
        val index = userList.indexOfFirst { it.id == id }
        if (index != -1) {
            userList[index] = userList[index].copy(name = newName)
        }
    }
}

// --- Screen Enum ---
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{userId}") {
        fun createRoute(userId: String) = "detail/$userId"
    }
    object Edit : Screen("edit/{userId}") {
        fun createRoute(userId: String) = "edit/$userId"
    }
}

// --- MainActivity ---
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Composecoffee4_3Theme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = viewModel()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(navController, viewModel)
                        }
                        composable(
                            route = Screen.Detail.route,
                            arguments = listOf(navArgument("userId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId") ?: ""
                            DetailScreen(userId, navController, viewModel)
                        }
                        composable(
                            route = Screen.Edit.route,
                            arguments = listOf(navArgument("userId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId") ?: ""
                            EditScreen(userId, navController, viewModel)
                        }
                    }
                }
            }
        }
    }
}

// --- HomeScreen ---
@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("사용자 목록", style = MaterialTheme.typography.titleLarge)
        LazyColumn {
            items(viewModel.userList) { user ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate(Screen.Detail.createRoute(user.id)) }
                        .padding(8.dp)
                ) {
                    Text(text = user.name)
                }
            }
        }
    }
}

// --- DetailScreen ---
@Composable
fun DetailScreen(userId: String, navController: NavController, viewModel: MainViewModel) {
    val user = viewModel.getUserById(userId)
    Column(modifier = Modifier.padding(16.dp)) {
        Text("상세 정보", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text("ID: ${user?.id ?: "없음"}")
        Text("이름: ${user?.name ?: "없음"}")
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { navController.navigate(Screen.Edit.createRoute(userId)) },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Blue
            )
        ) {
            Text("수정하기")
        }
    }
}

// --- EditScreen ---
@Composable
fun EditScreen(userId: String, navController: NavController, viewModel: MainViewModel) {
    val user = viewModel.getUserById(userId)
    var nameState by remember { mutableStateOf(TextFieldValue(user?.name ?: "")) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("이름 수정", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        TextField(
            value = nameState,
            onValueChange = { nameState = it },
            label = { Text("새 이름") }
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                viewModel.updateUserName(userId, nameState.text)
                navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF4CAF50)
            )
        ) {
            Text("저장")
        }
    }
}