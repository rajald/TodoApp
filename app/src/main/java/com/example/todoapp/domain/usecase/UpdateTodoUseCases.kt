package com.example.todoapp.domain.usecase

import com.example.todoapp.data.repository.TodoRepository
import javax.inject.Inject

class UpdateTodoUseCases @Inject constructor(private val repository: TodoRepository) {
    suspend operator fun invoke(id: Int) = repository.toggleTodo(id)
}