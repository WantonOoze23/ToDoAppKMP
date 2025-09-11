package vm

import com.tyshko.domain.model.ToDoModel
import com.tyshko.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeRepositoryImpl : ToDoRepository {
    private val fakeUserIp = "12.34.56.78"
    private val todos = MutableStateFlow<List<ToDoModel>>(emptyList())


    override fun getToDos(): Flow<List<ToDoModel>> {
        return todos
    }

    override suspend fun insertToDo(todo: ToDoModel): Boolean {
        val current = todos.value.toMutableList()
        return if (current.any { it.id == todo.id }) {
            false
        } else {
            current.add(todo)
            todos.value = current
            true
        }
    }

    override suspend fun getCertainToDo(id: Long): ToDoModel? {
        return todos.value.firstOrNull { it.id == id }
    }

    override suspend fun updateToDo(todo: ToDoModel): Boolean {
        val current = todos.value.toMutableList()
        val index = current.indexOfFirst { it.id == todo.id }
        return if (index != -1) {
            current[index] = todo
            todos.value = current
            true
        } else {
            false
        }
    }

    override suspend fun deleteToDO(id: Long): Boolean {
        val current = todos.value.toMutableList()
        val newList = current.filterNot { it.id == id }
        val removed = newList.size != current.size
        if (removed) {
            todos.value = newList
        }
        return removed
    }

    override suspend fun getUserIP(): String {
        return fakeUserIp
    }

    fun addTestTodos(todosToAdd: List<ToDoModel>) {
        val current = todos.value.toMutableList()
        current.addAll(todosToAdd)
        todos.value = current
    }
}