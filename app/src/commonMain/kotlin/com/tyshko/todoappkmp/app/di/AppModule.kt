package com.tyshko.todoappkmp.app.di


import com.tyshko.todoapp.vm.mvi.ToDoEditViewModel
import com.tyshko.todoapp.vm.mvvm.ToDoViewViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ToDoViewViewModel(get()) }
    viewModel { ToDoEditViewModel(get(), get()) }
}