package com.tyshko.todoappkmp.app.di


import com.tyshko.todoapp.vm.mvi.ToDoEditViewModel
import com.tyshko.todoapp.vm.mvvm.ToDoViewViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::ToDoViewViewModel)
    viewModel{
        ToDoEditViewModel(get())
    }
}