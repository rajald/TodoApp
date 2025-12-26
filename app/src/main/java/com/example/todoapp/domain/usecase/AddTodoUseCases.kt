package com.example.todoapp.domain.usecase

import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.domain.model.TodoItem
import javax.inject.Inject

class AddTodoUseCases @Inject constructor(private val repository: TodoRepository) {
    suspend operator fun invoke(todoItem: TodoItem) = repository.addTodo(todoItem)
}