package com.tyshko.todoappkmp.data.di

import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.tyshko.data.local.ToDoDataBase
import com.tyshko.domain.repository.ToDoRepository
import com.tyshko.todoappkmp.data.network.NetworkApi
import com.tyshko.todoappkmp.data.repository.ToDoRepositoryImpl
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual fun platformModule(): Module = module{
    single { provideDatabase(getDatabaseBuilder()) }.bind<ToDoDataBase>()
    single { get<ToDoDataBase>().toDoDao() }
    single { NetworkApi() }
    single<ToDoRepository> { ToDoRepositoryImpl(get(), get()) }
}

fun provideDatabase(builder: RoomDatabase.Builder<ToDoDataBase>): ToDoDataBase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

fun getDatabaseBuilder(): RoomDatabase.Builder<ToDoDataBase> {
    val dbFilePath = documentDirectory() + "/todo_db"
    return databaseBuilder<ToDoDataBase>(
        name = dbFilePath
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
