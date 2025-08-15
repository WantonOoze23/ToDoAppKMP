package com.tyshko.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tyshko.todoapp.ui.screens.LoadingScreen
import com.tyshko.todoapp.ui.screens.ToDosScreen
import com.tyshko.todoapp.ui.screens.ViewEditScreen
import com.tyshko.todoapp.vm.mvi.ToDoEditViewModel
import com.tyshko.todoapp.vm.mvvm.ToDoViewViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier

@Composable
fun ToDoNavigation(
    navController: NavHostController = rememberNavController(),
) {
    val todosViewModel = koinViewModel<ToDoViewViewModel>()
    NavHost(
        navController = navController,
        startDestination = "loading"
    ) {
        composable("loading"){
            LoadingScreen(
                onFinish = {
                    navController.navigate("main") {
                        popUpTo("loading") { inclusive = true }
                    }
                }
            )
        }
        composable("main"){
            ToDosScreen(
                viewModel = todosViewModel,
                navController = navController,
            )
        }
        composable("todo?todoId={todoId}"){ backStackEntry ->
            val toDoEditViewModel = koinViewModel<ToDoEditViewModel>(parametersOf(backStackEntry.savedStateHandle) as Qualifier?)
            val todoID = backStackEntry.arguments?.getString("todoId")?.toLongOrNull()
            ViewEditScreen(
                toDoId = todoID,
                navController = navController,
                viewModel = toDoEditViewModel
            )
        }
    }
}