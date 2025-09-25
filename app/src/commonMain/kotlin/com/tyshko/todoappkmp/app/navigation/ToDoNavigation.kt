package com.tyshko.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tyshko.todoapp.ui.screens.LoadingScreen
import com.tyshko.todoapp.ui.screens.ToDosScreen
import com.tyshko.todoapp.ui.screens.ViewEditScreen
import com.tyshko.todoapp.vm.mvi.ToDoEditViewModel
import com.tyshko.todoapp.vm.mvvm.ToDoViewViewModel
import com.tyshko.todoappkmp.app.navigation.Screen
import org.koin.compose.viewmodel.koinViewModel
@Composable
fun ToDoNavigation(
    navController: NavHostController = rememberNavController(),
) {
    val todosViewModel = koinViewModel<ToDoViewViewModel>()
    NavHost(
        navController = navController,
        startDestination = Screen.Loading.route
    ) {
        composable(Screen.Loading.route) {
            LoadingScreen(
                onFinish = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Loading.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Main.route) {
            ToDosScreen(
                viewModel = todosViewModel,
                onAddClick = {
                    navController.navigate(Screen.TodoViewEdit.route)
                },
                onEditClick = { todoId ->
                    navController.navigate(Screen.TodoViewEdit.createRoute(todoId))
                }
            )
        }
        composable(
            Screen.TodoViewEdit.route,
            arguments = listOf(navArgument("todoId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) {
            val toDoEditViewModel = koinViewModel<ToDoEditViewModel>()
            ViewEditScreen(
                onPopBackStack = { navController.popBackStack() },
                viewModel = toDoEditViewModel
            )
        }
    }
}