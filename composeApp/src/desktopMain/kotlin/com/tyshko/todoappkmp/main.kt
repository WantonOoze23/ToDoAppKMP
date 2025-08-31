package com.tyshko.todoappkmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.getString

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ToDoAppKMP",
    ) {
        App()
    }
}