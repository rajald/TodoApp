package com.example.todoapp.data.datasource

import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.domain.model.TodoItemData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(private val dao: TodoDao) : TodoRepository {
    override fun getTodos(): Flow<List<TodoItemData>> = dao.getTodos().map { list ->
        list.map { TodoItemData(id = it.id, title = it.title, isDone = it.isDone) }
    }

    override suspend fun addTodo(todoItemData: TodoItemData) {
        dao.insert(TodoEntity(title = todoItemData.title, isDone = todoItemData.isDone))
    }

    override suspend fun toggleTodo(id: Int) = dao.toggle(id)

    override suspend fun deleteTodos(ids: List<Int>) {
        dao.deleteTodos(idList = ids)
    }

    override suspend fun deleteTodo(todoItemData: TodoItemData) {
        dao.delete(TodoEntity(id = todoItemData.id, title = todoItemData.title, isDone = todoItemData.isDone))
    }
}