package com.example.todoapp.domain.model

data class TodoItemData(
    val id: Int,
    val title: String,
    var isDone: Boolean
)