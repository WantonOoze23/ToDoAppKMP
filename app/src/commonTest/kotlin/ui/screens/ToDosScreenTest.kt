package com.tyshko.todoapp.ui.screens

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.test.swipeLeft
import com.tyshko.domain.model.ToDoModel
import com.tyshko.todoapp.vm.mvvm.ToDoViewViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import vm.FakeRepositoryImpl
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ToDosScreenTest {

    private lateinit var viewModel: ToDoViewViewModel
    private lateinit var fakeRepository: FakeRepositoryImpl
    private var testTodos = listOf(

        ToDoModel(
            id = 1L,
            title = "First todo title",
            description = "First todo description",
            isCompleted = false
        ),
        ToDoModel(
            id = 2L,
            title = "Second todo title",
            description = "Second todo description",
            isCompleted = true
        ),
        ToDoModel(
            id = 3L,
            title = "Todo to delete",
            description = "Third todo description",
            isCompleted = false
        )
    )

    @BeforeTest
    fun setUp() {
        fakeRepository = FakeRepositoryImpl()
        viewModel = ToDoViewViewModel(fakeRepository)

        fakeRepository.addTestTodos(testTodos)

        val state = MutableStateFlow(testTodos)
    }

    @Test
    fun toDosScreen_showToDos() = runComposeUiTest{
        setContent({
            ToDosScreen(
                viewModel = viewModel,
                onAddClick = {},
                onEditClick = {}
            )
        })

        onNodeWithText("First todo title").assertIsDisplayed()
        onNodeWithText("Second todo title").assertIsDisplayed()
        onNodeWithText("Todo to delete").assertIsDisplayed()

        onNodeWithText("IP: 12.34.56.78").assertIsDisplayed()
    }

    @Test
    fun toDosScreen_swipeToDelete_callsDelete() = runComposeUiTest {
        setContent({
            ToDosScreen(
                viewModel = viewModel,
                onAddClick = {},
                onEditClick = {}
            )
        })

        onNodeWithText("Todo to delete")
            .performTouchInput {
                swipeLeft(startX = right - 10f, endX = left + 10f, durationMillis = 300)
            }

        onNodeWithText("Todo to delete").assertDoesNotExist()
    }

    @Test
    fun toDosScreen_makeToDoDone() = runComposeUiTest{
        setContent({
            ToDosScreen(
                viewModel = viewModel,
                onAddClick = {},
                onEditClick = {}
            )
        })

        val checkBox = onNodeWithContentDescription("CheckBox1")
        checkBox.assertExists()
        checkBox.performClick()

        checkBox.assertIsOn()
    }
}