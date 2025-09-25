package com.tyshko.todoappkmp.data.di

import com.tyshko.domain.repository.ToDoRepository
import com.tyshko.todoappkmp.data.network.NetworkApi
import com.tyshko.todoappkmp.data.repository.ToDoRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module{
    single<ToDoRepository> { ToDoRepositoryImpl(get()) }
    single{ NetworkApi() }
}