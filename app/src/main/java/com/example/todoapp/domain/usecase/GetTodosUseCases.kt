package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.model.TodoItemListState
import com.example.todoapp.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodosUseCases @Inject constructor(private val repository: TodoRepository) {
    suspend operator fun invoke(): Flow<TodoItemListState> = repository.getTodos()
}