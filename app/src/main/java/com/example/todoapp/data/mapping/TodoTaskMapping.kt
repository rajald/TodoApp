package com.example.todoapp.data.mapping

import com.example.todoapp.data.datasource.TodoEntity
import com.example.todoapp.domain.model.TodoItemData

fun TodoEntity.todoTaskMapping(): TodoItemData {
    return TodoItemData(
        id = this.id,
        book_title = this.title,
        isCompleted = this.isCompleted
    )
}