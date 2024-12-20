package com.example.todolist2
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ToDoViewModel : ViewModel(){
    private val repository = ToDoRepository()

    private val _toDoList = MutableLiveData<List<ToDoModel>>(emptyList())
    val toDoList: LiveData<List<ToDoModel>> get() = _toDoList

    fun loadToDoItems() {
        repository.getToDoItems(
            onSuccess = { items -> _toDoList.value = items },
            onFailure = { /* Handle error */ }
        )
    }

    fun addToDoItem(item: ToDoModel) {
        repository.addToDoItem(item,
            onSuccess = { loadToDoItems() },
            onFailure = { /* Handle error */ }
        )
    }

    fun deleteToDoItem(id: String) {
        repository.deleteToDoItem(id,
            onSuccess = { loadToDoItems() },
            onFailure = { /* Handle error */ }
        )
    }

}