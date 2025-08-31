package com.tyshko.todoappkmp

import androidx.compose.ui.window.ComposeUIViewController
import com.tyshko.todoappkmp.data.di.dataModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController{
    App()
}