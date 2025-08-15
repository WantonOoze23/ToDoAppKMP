package com.tyshko.todoappkmp.data.di

import androidx.room.Room
import com.tyshko.data.local.ToDoDataBase
import com.tyshko.domain.repository.ToDoRepository
import com.tyshko.todoappkmp.data.repository.ToDoRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun platformModule() : Module = module {
    singleOf(constructor = ::ToDoRepositoryImpl).bind<ToDoRepository>()
    single{
        provideDatabase()
    }.bind<ToDoDataBase>()
    single { get<ToDoDataBase>().toDoDao() }
}
fun provideDatabase() : ToDoDataBase {
    return Room.databaseBuilder<ToDoDataBase>(
        "todo_db"
    ).build()
}