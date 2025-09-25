package com.tyshko.todoappkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.tyshko.todoapp.navigation.ToDoNavigation
import com.tyshko.todoappkmp.app.di.appModule
import com.tyshko.todoappkmp.data.di.dataModule
import com.tyshko.todoappkmp.data.di.platformModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.core.module.Module

@Composable
@Preview
fun App(
    platformModule : Module = Module()
) {
    KoinApplication(
        application = {
            modules(platformModule, appModule, dataModule)
        }
    ){
        MaterialTheme {
            ToDoNavigation()
        }
    }

}