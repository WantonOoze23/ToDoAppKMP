package com.tyshko.todoappkmp.data.repository

import com.tyshko.domain.model.ToDoModel
import com.tyshko.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class ToDoRepositoryImpl : ToDoRepository {
    override fun getToDos(): Flow<List<ToDoModel>>
    override suspend fun insertToDo(todo: ToDoModel): Boolean
    override suspend fun getCertainToDo(id: Long): ToDoModel?
    override suspend fun updateToDo(todo: ToDoModel): Boolean
    override suspend fun deleteToDO(id: Long): Boolean
    override suspend fun getUserIP(): String
}