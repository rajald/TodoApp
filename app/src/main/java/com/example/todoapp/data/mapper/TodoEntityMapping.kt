package com.example.todoapp.data.mapper

import com.example.todoapp.data.datasource.TodoEntity
import com.example.todoapp.domain.model.TodoItemData

fun TodoItemData.todoEntityMapping(): TodoEntity{
    return TodoEntity(
        id = this.id,
        title = this.book_title,
        isCompleted = this.isCompleted
    )
}