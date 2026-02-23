package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.model.TodoItemData
import com.example.todoapp.domain.repository.TodoRepository
import javax.inject.Inject


class DeleteTodoUseCases @Inject constructor(private val repository: TodoRepository){
    suspend operator fun invoke(ids: List<Int>) = repository.deleteTodos(ids)
    suspend operator fun invoke(todoItemData: TodoItemData) = repository.deleteTodo(todoItemData)
}