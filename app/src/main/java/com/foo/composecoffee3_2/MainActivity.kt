package com.foo.composecoffee3_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.foo.composecoffee3_2.ui.theme.Composecoffee3_2Theme

// 데이터 클래스
data class TodoItem(val id: Int, val task: String, var isDone: Boolean = false)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Composecoffee3_2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TodoAppScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Composable
fun TodoAppScreen1(viewModel: TodoViewModel = remember { TodoViewModel() }) {
    val todoList = viewModel.todoList
    val newTask = viewModel.newTask.value

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newTask,
                onValueChange = { viewModel.onTaskChange(it) },
                modifier = Modifier.weight(1f),
                placeholder = { Text("할 일 입력") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.addTask() }) {
                Text("추가")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(todoList, key = { it.id }) { todo ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Checkbox(
                        checked = todo.isDone,
                        onCheckedChange = { viewModel.toggleDone(todo, it) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Green,
                            uncheckedColor = Color.Gray,
                            checkmarkColor = Color.White
                        )
                    )
                    Text(
                        text = todo.task,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        textDecoration = if (todo.isDone) TextDecoration.LineThrough else TextDecoration.None,
                        color = if (todo.isDone) Color.Gray else Color.Unspecified
                    )
                    Button(onClick = { viewModel.deleteTask(todo) }) {
                        Text("삭제")
                    }
                }
            }
        }
    }
}

@Composable
fun TodoAppScreen(modifier: Modifier = Modifier) {
    val todoList = remember { mutableStateListOf<TodoItem>() }
    var newTask by remember { mutableStateOf("") }
    var nextId by remember { mutableStateOf(0) }

    Column(modifier = modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newTask,
                onValueChange = { newTask = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("할 일 입력") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (newTask.isNotBlank()) {
                    todoList.add(TodoItem(id = nextId, task = newTask))
                    nextId++
                    newTask = ""
                }
            },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Blue,
                    containerColor = Color.Cyan
                )) {
                Text("추가")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(todoList, key = { it.id }) { todo ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Checkbox(
                        checked = todo.isDone,
                        onCheckedChange = {
                            val index = todoList.indexOf(todo)
                            if (index != -1) {
                                todoList[index] = todo.copy(isDone = it)
                            }
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Green,        // 체크됐을 때 색
                            uncheckedColor = Color.LightGray,  // 체크 안됐을 때 색
                            checkmarkColor = Color.White       // ✔ 표시 색
                        )
                    )

                    Text(
                        text = todo.task,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        textDecoration = if (todo.isDone) TextDecoration.LineThrough else TextDecoration.None,
                        color = if (todo.isDone) Color.Gray else Color.Unspecified
                    )
                    Button(
                        onClick = { todoList.remove(todo) },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Blue,
                            containerColor = Color.Cyan
                        )
                    ) {

                        Text("삭제")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoAppPreview() {
    Composecoffee3_2Theme {
        TodoAppScreen1()
    }
}
