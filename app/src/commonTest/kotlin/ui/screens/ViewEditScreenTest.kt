package com.tyshko.todoapp.ui.screens

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.test.runComposeUiTest
import androidx.lifecycle.SavedStateHandle
import com.tyshko.domain.model.ToDoModel
import com.tyshko.todoapp.vm.mvi.ToDoEditViewModel
import com.tyshko.todoapp.vm.mvi.ToDoIntent
import vm.FakeRepositoryImpl
import kotlin.test.BeforeTest
import kotlin.test.Test


@OptIn(ExperimentalTestApi::class)
class ViewEditScreenTest {


    private lateinit var viewModel: ToDoEditViewModel
    private lateinit var fakeRepository: FakeRepositoryImpl

    private var testTodo = ToDoModel(
                    id = 1L,
                    title = "First todo title",
                    description = "First todo description",
                    isCompleted = false
                )


    @BeforeTest
    fun setUp() {
        fakeRepository = FakeRepositoryImpl()
        viewModel = ToDoEditViewModel(fakeRepository, savedStateHandle = SavedStateHandle())

        viewModel.onIntent(ToDoIntent.SetTitle(testTodo.title))
        viewModel.onIntent(ToDoIntent.SetDescription(testTodo.description))
        viewModel.onIntent(ToDoIntent.SetCompleted(testTodo.isCompleted))
    }

    @Test
    fun editScreen_showsToDoData() = runComposeUiTest {
        setContent({
            ViewEditScreen(
                viewModel = viewModel,
                onPopBackStack = {},
            )
        })
        onNodeWithText("First todo title").assertExists()
        onNodeWithText("First todo description").assertExists()
    }

    @Test
    fun editScreen_canEditTitleAndDescription() = runComposeUiTest {
        setContent({
            ViewEditScreen(
                viewModel = viewModel,
                onPopBackStack = {},
            )
        })

        val newTitle = "Updated Title"
        val newDesc = "Updated Description"

        onNodeWithText("First todo title").performTextReplacement(newTitle)
        onNodeWithText("First todo description").performTextReplacement(newDesc)

        onNodeWithText(newTitle).assertExists()
        onNodeWithText(newDesc).assertExists()
    }

    @Test
    fun editScreen_toggleIsCompleted() = runComposeUiTest {
        setContent({
            ViewEditScreen(
                viewModel = viewModel,
                onPopBackStack = {},
            )
        })

        val checkbox = onNodeWithContentDescription("Checkbox")
        checkbox.assertExists()
        checkbox.assertIsOff()
        checkbox.performClick()
        checkbox.assertIsOn()
    }
}