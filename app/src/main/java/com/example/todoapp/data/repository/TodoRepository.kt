package com.example.todoapp.data.repository

import com.example.todoapp.domain.model.TodoItemData
import com.example.todoapp.domain.model.TodoItemListState
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun getTodos(): Flow<TodoItemListState>
    suspend fun addTodo(todoItemData: TodoItemData)
    suspend fun toggleTodo(todoItemData: TodoItemData)
    suspend fun deleteTodos(ids: List<Int>)
    suspend fun deleteTodo(todoItemData: TodoItemData)
}