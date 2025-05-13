package com.example.week_03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.week_03.ui.theme.Week_03Theme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week_03Theme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    TodoApp(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListScreenPreview() {
    Week_03Theme {
        TodoApp()
    }
}

@Composable
fun TodoListScreen(modifier: Modifier = Modifier) {
    var todoList = remember { mutableStateListOf("과제하기", "산책하기") }
    var newTodo by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Row {
            TextField(value = newTodo, onValueChange = { newTodo = it })
            Button(onClick = {
                if (newTodo.isNotBlank()) {
                    todoList.add(newTodo)
                    newTodo = ""
                }
            }) {
                Text("추가")
            }
        }

        LazyColumn {
            items(todoList) { item ->
                Text(text = item, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

data class TodoItem(val task: String, var isDone: Boolean = false)

@Composable
fun TodoCheckListScreen(modifier: Modifier = Modifier) {
    val todos = remember {
        mutableStateListOf(
            TodoItem("Jetpack Compose 공부"),
            TodoItem("운동하기"),
            TodoItem("빨래하기")
        )
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(todos) { todo ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { todo.isDone = !todo.isDone }
                    .padding(8.dp)
            ) {
                Checkbox(checked = todo.isDone, onCheckedChange = {
                    todo.isDone = it
                })
                Text(
                    text = todo.task,
                    textDecoration = if (todo.isDone) TextDecoration.LineThrough else TextDecoration.None
                )
            }
        }
    }
}

@Composable
fun TodoApp(modifier: Modifier = Modifier) {
    var newTodo by remember { mutableStateOf("") }
    val todos = remember { mutableStateListOf<TodoItem>() }

    Column(Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = newTodo,
                onValueChange = { newTodo = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("할 일을 입력하세요") }
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                if (newTodo.isNotBlank()) {
                    todos.add(TodoItem(newTodo))
                    newTodo = ""
                }
            }) {
                Text("추가")
            }
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            itemsIndexed(todos) { index, todo ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Checkbox(
                        checked = todo.isDone,
                        onCheckedChange = { checked ->
                            todos[index] = todo.copy(isDone = checked)
                        }
                    )
                    Text(
                        text = todo.task,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                            .clickable {
                                todos[index] = todo.copy(isDone = !todo.isDone)
                            },
                        color = if (todo.isDone) Color.Gray else Color.Unspecified,
                        textDecoration = if (todo.isDone) TextDecoration.LineThrough else TextDecoration.None
                    )
                    IconButton(onClick = { todos.removeAt(index) }) {
                        Icon(Icons.Default.Delete, contentDescription = "삭제", tint = Color.Red)
                    }
                }
            }
        }
    }
}

