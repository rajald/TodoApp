package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.model.TodoItemData
import com.example.todoapp.domain.repository.TodoRepository
import javax.inject.Inject

class UpdateTodoUseCases @Inject constructor(private val repository: TodoRepository) {
    suspend operator fun invoke(todoItemData: TodoItemData) = repository.toggleTodo(todoItemData)
}