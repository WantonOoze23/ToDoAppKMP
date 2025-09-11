package com.tyshko.todoapp.vm.mvi

import androidx.lifecycle.SavedStateHandle
import com.tyshko.domain.model.ToDoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import vm.FakeRepositoryImpl
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class ToDoEditViewModelTest {

    private lateinit var viewModel: ToDoEditViewModel
    private lateinit var repository: FakeRepositoryImpl

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        repository = FakeRepositoryImpl()
        viewModel = ToDoEditViewModel(repository, SavedStateHandle())
    }


    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onIntent isToDoGet - updates state with todo`() = runTest {
        val todo = ToDoModel(
            id = 1,
            title = "Test title",
            description = "Test description",
            isCompleted = true
        )
        repository.insertToDo(todo)

        viewModel.onIntent(ToDoIntent.isToDoGet(1))
        advanceUntilIdle()

        val state = viewModel.toDoState.value
        assertEquals(todo.id, state.id)
        assertEquals(todo.title, state.title)
        assertEquals(todo.description, state.description)
        assertEquals(todo.isCompleted, state.isCompleted)
        assertTrue(state.isToDoGet)
    }

    @Test
    fun `onIntent SetTitle - updates title in state`() = runTest {
        viewModel.onIntent(ToDoIntent.SetTitle("New title"))
        val state = viewModel.toDoState.value
        assertEquals("New title", state.title)
    }

    @Test
    fun `onIntent SavaToDo - inserts todo when data valid`() = runTest {
        viewModel.onIntent(ToDoIntent.SetTitle("Hello"))
        viewModel.onIntent(ToDoIntent.SetDescription("World"))
        viewModel.onIntent(ToDoIntent.SetCompleted(true))

        viewModel.onIntent(ToDoIntent.SavaToDo)
        advanceUntilIdle()

        val todos: List<ToDoModel> = repository.getToDos().first()

        assertEquals(1, todos.size)
        val savedTodo = todos.first()
        assertEquals("Hello", savedTodo.title)
        assertEquals("World", savedTodo.description)
        assertTrue(savedTodo.isCompleted)
    }

    @Test
    fun `onIntent SavaToDo - does not insert when title or description blank`() = runTest {
        viewModel.onIntent(ToDoIntent.SetTitle(""))
        viewModel.onIntent(ToDoIntent.SetDescription(""))

        viewModel.onIntent(ToDoIntent.SavaToDo)
        advanceUntilIdle()

        val todos = repository.getToDos().first()
        assertTrue(todos.isEmpty())
    }
}
