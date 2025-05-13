package com.example.week_04

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument

import androidx.compose.material3.TextField
import androidx.compose.runtime.*

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController, viewModel = viewModel())
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: "Unknown"
            DetailScreen(userId, navController, viewModel = viewModel())
        }
        composable(
            route = "edit/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: "Unknown"
            EditScreen(userId, navController, viewModel = viewModel())
        }
    }
}

@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel) {
    val users = viewModel.userList
    LazyColumn {
        items(users) { user ->
            Text(text = user.name, modifier = Modifier.clickable {
                navController.navigate(Screen.Detail.createRoute(user.id))
            })
        }
    }
}

@Composable
fun DetailScreen(userId: String, navController: NavController, viewModel: MainViewModel) {
    val user = viewModel.getUserById(userId)
    Column {
        Text("이름: ${user?.name ?: "Unknown"}")
        Button(onClick = {
            navController.navigate("edit/$userId")
        }) {
            Text("수정하기")
        }
    }
}

@Composable
fun EditScreen(userId: String, navController: NavController, viewModel: MainViewModel) {
    val user = viewModel.getUserById(userId)
    var newName by remember { mutableStateOf(user?.name ?: "") }

    Column {
        Text(text = "현재 이름: ${user?.name ?: "Unknown"}")
        TextField(
            value = newName,
            onValueChange = { newName = it },
            placeholder = { Text("새 이름 입력") }
        )
        Button(onClick = {
            if (newName.isNotBlank()) {
                viewModel.updateUserName(userId, newName)
                navController.popBackStack() // 이전화면(Detail)으로 돌아감
            }
        }) {
            Text("저장")
        }
    }
}