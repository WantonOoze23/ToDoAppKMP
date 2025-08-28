package com.tyshko.todoappkmp.app.di


import com.tyshko.domain.repository.ToDoRepository
import com.tyshko.todoapp.vm.mvi.ToDoEditViewModel
import com.tyshko.todoapp.vm.mvvm.ToDoViewViewModel
import com.tyshko.todoappkmp.data.repository.ToDoRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModel { ToDoViewViewModel(get()) }
    viewModel { ToDoEditViewModel(get()) }
}