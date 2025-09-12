package com.tyshko.todoappkmp.data.di

import androidx.room.Room.databaseBuilder
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.tyshko.data.local.ToDoDataBase
import com.tyshko.domain.repository.ToDoRepository
import com.tyshko.todoappkmp.data.network.NetworkApi
import com.tyshko.todoappkmp.data.repository.ToDoRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun platformModule() : Module = module {
    singleOf(::ToDoRepositoryImpl).bind<ToDoRepository>()
    single{
        provideDatabase()
    }.bind<ToDoDataBase>()
    single { NetworkApi() }
    single { get<ToDoDataBase>().toDoDao() }
}
fun provideDatabase() : ToDoDataBase {
    return databaseBuilder<ToDoDataBase>(
        "todo_db"
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}