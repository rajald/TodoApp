package com.example.todoapp.data.repository

import android.util.Log
import com.example.todoapp.data.datasource.TodoDao
import com.example.todoapp.data.mapping.todoEntityMapping
import com.example.todoapp.data.mapping.todoTaskMapping
import com.example.todoapp.data.network.ApiService
import com.example.todoapp.data.network.NetworkResult
import com.example.todoapp.data.network.safeApiCall
import com.example.todoapp.domain.model.TodoItemData
import com.example.todoapp.domain.model.TodoItemListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val dao: TodoDao,
    private val api: ApiService
) : TodoRepository {
    override suspend fun getTodos(): Flow<TodoItemListState> = flow {
        // 1. Emit initial state with local data
        val todosFromDB = dao.getTodos().map { list ->
            list.map { it.todoTaskMapping() }
        }
        todosFromDB.collect { todos ->
            emit(TodoItemListState(todoItemList = todos))
        }

        // 2. Start network call (The "Automatic Refresh")
        val result = safeApiCall { api.getTodoTasks() }

        when (result) {
            is NetworkResult.Success -> {
                // Save to local database
                result.data?.let { todoItems ->
                    val todoItemEntity = todoItems.map { todoItem ->
                        todoItem.todoEntityMapping()
                    }
                    dao.insertAll(todoItemEntity) // Update local DB

                    // 3. Emit success state with new data
                    emit(TodoItemListState(todoItemList = result.data))
                }
            }

            is NetworkResult.Error -> {
                // 4. Emit error with local data
                // Log error or notify UI of sync failure
                todosFromDB.collect { localTodoList ->
                    emit(
                        TodoItemListState(
                            todoItemList = localTodoList,
                            errorMessage = "${result.code}, ${result.message}"
                        )
                    )
                }
            }

            is NetworkResult.Exception -> {
                // 5. Emit Exception with local data
                // Log exception (e.g., No Internet)
                todosFromDB.collect { localTodoList ->
                    emit(
                        TodoItemListState(
                            todoItemList = localTodoList,
                            errorMessage = result.e.toString()
                        )
                    )
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addTodo(todoItemData: TodoItemData) {
        val result = safeApiCall { api.addTodoTasks(todoItemData = todoItemData) }
        when (result) {
            is NetworkResult.Error -> {
                Log.d("TodoRepoImpl=>", result.message.toString())
            }

            is NetworkResult.Exception -> {
                Log.d("TodoRepoImpl=>", result.e.toString())
            }

            is NetworkResult.Success -> {
                Log.d("TodoRepoImpl=>", result.data?.id.toString())
                val todoItem = result.data as TodoItemData
                dao.insert(
                    todoItem.todoEntityMapping()
                )
            }
        }
    }

    override suspend fun toggleTodo(todoItemData: TodoItemData) {
        val result = safeApiCall {
            api.updateCompleteStatus(
                taskId = todoItemData.id.toString(),
                todoItemData = todoItemData
            )
        }
        when (result) {
            is NetworkResult.Error -> {
                Log.d("TodoRepoImpl=>", result.message.toString())
            }

            is NetworkResult.Exception -> {
                Log.d("TodoRepoImpl=>", result.e.toString())
            }

            is NetworkResult.Success<*> -> {
                Log.d("TodoRepoImpl=>", "${result.data}")
                dao.toggle(todoItemData.id)
            }
        }
    }

    override suspend fun deleteTodos(ids: List<Int>) {
        dao.deleteTodos(idList = ids)
    }

    override suspend fun deleteTodo(todoItemData: TodoItemData) {
        val result = safeApiCall { api.deleteTodoTask(todoItemData.id.toString()) }
        when (result) {
            is NetworkResult.Error -> {
                Log.d("TodoRepoImpl=>", result.message.toString())
            }

            is NetworkResult.Exception -> {
                Log.d("TodoRepoImpl=>", result.e.toString())
            }

            is NetworkResult.Success<*> -> {
                Log.d("TodoRepoImpl=>", "${result.data}")
                dao.delete(
                    todoItemData.todoEntityMapping()
                )
            }
        }
    }
}