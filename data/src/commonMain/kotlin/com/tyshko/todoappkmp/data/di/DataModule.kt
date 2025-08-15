package com.tyshko.todoappkmp.data.di

import com.tyshko.todoappkmp.data.repository.ToDoRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    includes(dataModule())
}

expect fun dataModule(): Module