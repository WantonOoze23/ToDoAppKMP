package com.tyshko.todoapp.vm.mvvm

import com.tyshko.domain.model.ToDoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import vm.FakeRepositoryImpl
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class ToDoViewViewModelTest {

    private lateinit var repository: FakeRepositoryImpl
    private lateinit var toDoViewViewModel: ToDoViewViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        repository = FakeRepositoryImpl()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init - loads todos and ip successfully`() = runTest {
        val todos = listOf(
            ToDoModel(1, "Test 1", "Desc 1", false),
            ToDoModel(2, "Test 2", "Desc 2", true)
        )
        todos.forEach { repository.insertToDo(it) }

        toDoViewViewModel = ToDoViewViewModel(repository)
        advanceUntilIdle()

        assertEquals("11.12.20.10", toDoViewViewModel.publicIP.value)
        assertEquals(todos, toDoViewViewModel.todos.value)
    }

    @Test
    fun `init - handles empty data gracefully`() = runTest {
        toDoViewViewModel = ToDoViewViewModel(repository)
        advanceUntilIdle()

        assertEquals("11.12.20.10", toDoViewViewModel.publicIP.value)
        assertEquals(emptyList<ToDoModel>(), toDoViewViewModel.todos.value)
    }

    @Test
    fun `deleteToDo - removes correct item`() = runTest {
        val todo = ToDoModel(42, "Test", "Desc", false)
        repository.insertToDo(todo)

        toDoViewViewModel = ToDoViewViewModel(repository)
        advanceUntilIdle()

        assertEquals(listOf(todo), toDoViewViewModel.todos.value)

        toDoViewViewModel.deleteToDo(42)
        advanceUntilIdle()

        assertEquals(emptyList<ToDoModel>(), toDoViewViewModel.todos.value)
    }

    @Test
    fun `onCheckClick - toggles isCompleted and updates todo`() = runTest {
        val todo = ToDoModel(1, "Test", "Desc", isCompleted = false)
        repository.insertToDo(todo)

        toDoViewViewModel = ToDoViewViewModel(repository)
        advanceUntilIdle()

        toDoViewViewModel.onCheckClick(todo)
        advanceUntilIdle()

        val updated = toDoViewViewModel.todos.value.first()
        assertTrue(updated.isCompleted)
    }
}
