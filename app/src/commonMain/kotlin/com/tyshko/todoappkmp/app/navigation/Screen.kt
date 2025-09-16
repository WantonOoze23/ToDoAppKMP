package com.tyshko.todoappkmp.app.navigation

sealed class Screen(val route: String) {
    object Loading : Screen("loading")
    object Main : Screen("main")
    object TodoViewEdit : Screen("todo?todoId={todoId}") {
        fun createRoute(todoId: Long) = "todo?todoId=$todoId"
    }
}