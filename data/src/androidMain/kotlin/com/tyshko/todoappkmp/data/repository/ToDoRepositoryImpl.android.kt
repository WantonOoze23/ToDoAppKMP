package com.tyshko.todoappkmp.data.repository

import android.util.Log
import com.tyshko.data.local.ToDoDao
import com.tyshko.data.local.entity.ToDoEntity
import com.tyshko.domain.model.ToDoModel
import com.tyshko.domain.repository.ToDoRepository
import com.tyshko.todoappkmp.data.network.NetworkApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ToDoRepositoryImpl(
    private val toDoDao: ToDoDao,
    private val networkApi: NetworkApi
) : ToDoRepository {
    actual override fun getToDos(): Flow<List<ToDoModel>> {
        return toDoDao.getToDos().map { toDoEntities ->
            toDoEntities.map {
                it.toTodoModel()
            }
        }
    }

    actual override suspend fun insertToDo(todo: ToDoModel): Boolean {
        return try {
            toDoDao.insertToDO(ToDoEntity.fromToDoModel(todo))
            true
        } catch (e: Exception){
            Log.e("DB","Unable to INSERT ToDo: $e")
            false
        }
    }

    actual override suspend fun getCertainToDo(id: Long): ToDoModel? {
        return toDoDao.getCertainToDO(id)?.toTodoModel()
    }

    actual override suspend fun updateToDo(todo: ToDoModel): Boolean {
        return try{
            toDoDao.updateToDo(ToDoEntity.fromToDoModel(todo))
            true
        } catch (e: Exception){
            Log.e("DB","Unable to UPDATE ToDo: $e")
            false
        }
    }

    actual override suspend fun deleteToDO(id: Long): Boolean {
        return try{
            toDoDao.deleteToDO(id)
            true
        } catch (e: Exception){
            Log.e("DB","Unable to DELETE ToDo: $e")
            false
        }
    }

    actual override suspend fun getUserIP(): String {
        return networkApi.getUserIP()
    }
}