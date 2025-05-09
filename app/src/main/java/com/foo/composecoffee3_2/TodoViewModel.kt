package com.foo.composecoffee3_2


import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class TodoViewModel : ViewModel() {
    var newTask = mutableStateOf("")
        private set

    var todoList = mutableStateListOf<TodoItem>()
        private set

    private var nextId = 0

    fun onTaskChange(newText: String) {
        newTask.value = newText
    }

    fun addTask() {
        val task = newTask.value
        if (task.isNotBlank()) {
            todoList.add(TodoItem(id = nextId++, task = task))
            newTask.value = ""
        }
    }

    fun toggleDone(todo: TodoItem, isDone: Boolean) {
        val index = todoList.indexOf(todo)
        if (index != -1) {
            todoList[index] = todo.copy(isDone = isDone)
        }
    }

    fun deleteTask(todo: TodoItem) {
        todoList.remove(todo)
    }
}
