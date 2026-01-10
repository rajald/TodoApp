package com.example.todoapp.domain.model

data class TodoItemData(
    val id: Int,
    val book_title: String,
    var isCompleted: Boolean
)