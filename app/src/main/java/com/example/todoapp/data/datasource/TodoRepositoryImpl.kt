package com.example.todoapp.data.datasource

import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(private val dao: TodoDao) : TodoRepository {
    override fun getTodos(): Flow<List<TodoItem>> = dao.getTodos().map { list ->
        list.map { TodoItem(id = it.id, title = it.title, isDone = it.isDone) }
    }

    override suspend fun addTodo(todoItem: TodoItem) {
        dao.insert(TodoEntity(title = todoItem.title, isDone = todoItem.isDone))
    }

    override suspend fun toggleTodo(id: Int) = dao.toggle(id)

    override suspend fun deleteTodos(ids: List<Int>) {
        dao.deleteTodos(idList = ids)
    }

    override suspend fun deleteTodo(todoItem: TodoItem) {
        dao.delete(TodoEntity(id = todoItem.id, title = todoItem.title, isDone = todoItem.isDone))
    }
}