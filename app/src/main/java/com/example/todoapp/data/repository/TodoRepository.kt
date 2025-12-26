package com.example.todoapp.data.repository

import com.example.todoapp.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(): Flow<List<TodoItem>>
    suspend fun addTodo(todoItem: TodoItem)
    suspend fun toggleTodo(id: Int)
    suspend fun deleteTodos(ids: List<Int>)
    suspend fun deleteTodo(todoItem: TodoItem)
}