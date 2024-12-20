package com.example.todolist2
import com.google.firebase.firestore.FirebaseFirestore

class ToDoRepository {
    private val db = FirebaseFirestore.getInstance()
    private val toDoCollection = db.collection("todos")

    fun addToDoItem(item: ToDoModel, onSuccess:() -> Unit, onFailure: (Exception) -> Unit) {
        toDoCollection.document(item.id).set(item)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
    fun deleteToDoItem(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        toDoCollection.document(id).delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
        }

    fun getToDoItems(onSuccess: (List<ToDoModel>) -> Unit, onFailure: (Exception) -> Unit) {
        toDoCollection.get()
                .addOnSuccessListener { result ->
                    val items = result.map { it.toObject(ToDoModel::class.java) }
                    onSuccess(items)
                }
                .addOnFailureListener { onFailure(it) }
        }
    }
