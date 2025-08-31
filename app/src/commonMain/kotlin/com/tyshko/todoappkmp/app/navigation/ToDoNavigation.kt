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
import org.koin.compose.viewmodel.koinViewModel
import org.jetbrains.compose.resources.getString
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
        composable(
            "todo?todoId={todoId}",
            arguments = listOf(navArgument("todoId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) {
            val toDoEditViewModel = koinViewModel<ToDoEditViewModel>()
            ViewEditScreen(
                navController = navController,
                viewModel = toDoEditViewModel
            )
        }
    }
}