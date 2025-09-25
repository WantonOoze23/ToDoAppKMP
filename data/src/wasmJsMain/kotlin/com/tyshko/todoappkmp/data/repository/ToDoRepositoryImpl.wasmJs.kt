package com.tyshko.todoappkmp.data.repository

import com.tyshko.domain.model.ToDoModel
import com.tyshko.domain.repository.ToDoRepository
import com.tyshko.todoappkmp.data.network.NetworkApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ToDoRepositoryImpl(private val networkApi: NetworkApi) : ToDoRepository {

    private val startingTodos = mutableListOf(
        ToDoModel(id = 1, title = "Go to gym", description = "New gym opened last week", isCompleted = false),
        ToDoModel(id = 2, title = "Buy food", description = "Bread, cheese, milk, chocolate", isCompleted = false),
        ToDoModel(id = 3, title = "Visit grandparents", description = "Tickets are already bought", isCompleted = false),
        ToDoModel(id = 4, title = "Watch new video", description = "New video on YT youtube.com", isCompleted = false),
        ToDoModel(id = 5, title = "Read a book", description = "take some time to read a new one", isCompleted = false),
    )
    private val _todoState = MutableStateFlow(startingTodos.toList())

    actual override fun getToDos(): Flow<List<ToDoModel>> {
        return _todoState
    }

    actual override suspend fun insertToDo(todo: ToDoModel): Boolean {
        val exists = startingTodos.any { it.id == todo.id }
        return if (!exists) {
            startingTodos.add(todo)
            _todoState.value = startingTodos.toList()
            true
        } else false
    }

    actual override suspend fun getCertainToDo(id: Long): ToDoModel? {
        return startingTodos.find { it.id == id }
    }

    actual override suspend fun updateToDo(todo: ToDoModel): Boolean {
        val index = startingTodos.indexOfFirst { it.id == todo.id }
        return if (index != -1) {
            startingTodos[index] = todo
            _todoState.value = startingTodos.toList()
            true
        } else false
    }

    actual override suspend fun deleteToDO(id: Long): Boolean {
        val index = startingTodos.indexOfFirst { it.id == id }
        if (index != -1) {
            startingTodos.removeAt(index)
            _todoState.update { startingTodos.toList() }
        }
        return true
    }

    actual override suspend fun getUserIP(): String {
        return networkApi.getUserIP()
    }
}