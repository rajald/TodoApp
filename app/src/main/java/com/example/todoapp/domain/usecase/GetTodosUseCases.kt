package com.example.todoapp.domain.usecase

import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodosUseCases @Inject constructor(private val repository: TodoRepository) {
    operator fun invoke(): Flow<List<TodoItem>> = repository.getTodos()
}