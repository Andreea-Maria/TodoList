package com.example.todolist2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todolist2.ui.theme.ToDoList2Theme
import java.util.UUID



class MainActivity : ComponentActivity() {
    private val toDoViewModel: ToDoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListScreen(viewModel = toDoViewModel)
        }
    }
}


@Composable
fun ToDoListScreen(viewModel: ToDoViewModel) {
    val toDoList by viewModel.toDoList.observeAsState(emptyList())

    Column {
        // Add Item Input
        var newToDoTitle by remember { mutableStateOf("") }
        Row {
            TextField(
                value = newToDoTitle,
                onValueChange = { newToDoTitle = it },
                placeholder = { Text("New To-Do") }
            )
            Button(onClick = {
                if (newToDoTitle.isNotBlank()) {
                    val newItem = ToDoModel(
                        id = UUID.randomUUID().toString(),
                        title = newToDoTitle
                    )
                    viewModel.addToDoItem(newItem)
                    newToDoTitle = ""
                }
            }) {
                Text("Add")
            }
        }

        // To-Do List
        LazyColumn {
            items(toDoList) { item ->
                ToDoItemRow(item, onToggle = {
                    val updatedItem = item.copy(isCompleted = !item.isCompleted)
                    viewModel.addToDoItem(updatedItem)
                }, onDelete = { viewModel.deleteToDoItem(item.id) })
            }
        }
    }
}

@Composable
fun ToDoItemRow(item: ToDoModel, onToggle: () -> Unit, onDelete: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = item.isCompleted,
            onCheckedChange = { onToggle() }
        )
        Text(item.title, Modifier.weight(1f))
        IconButton(onClick = { onDelete() }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToDoListScreenPreview() {
    ToDoList2Theme {
        ToDoListScreen(viewModel = ToDoViewModel())
    }
}
