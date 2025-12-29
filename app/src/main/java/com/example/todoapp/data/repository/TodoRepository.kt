package com.example.todoapp.data.repository

import com.example.todoapp.domain.model.TodoItemData
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(): Flow<List<TodoItemData>>
    suspend fun addTodo(todoItemData: TodoItemData)
    suspend fun toggleTodo(id: Int)
    suspend fun deleteTodos(ids: List<Int>)
    suspend fun deleteTodo(todoItemData: TodoItemData)
}