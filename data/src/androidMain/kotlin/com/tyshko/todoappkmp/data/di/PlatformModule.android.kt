package com.tyshko.todoappkmp.data.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.tyshko.data.local.ToDoDataBase
import com.tyshko.domain.repository.ToDoRepository
import com.tyshko.todoappkmp.data.network.NetworkApi
import com.tyshko.todoappkmp.data.repository.ToDoRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun platformModule() : Module = module {
    single{
        provideDatabase(get())
    }.bind<ToDoDataBase>()
    single { get<ToDoDataBase>().toDoDao() }
    single { NetworkApi() }
    single<ToDoRepository> { ToDoRepositoryImpl(get(), get()) }
}
fun provideDatabase(context: Context) : ToDoDataBase{
    return databaseBuilder(
        context.applicationContext, ToDoDataBase::class.java, "todo_db"
    ).build()
}
