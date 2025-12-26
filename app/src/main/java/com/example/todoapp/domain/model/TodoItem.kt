package com.example.todoapp.domain.model

data class TodoItem(
    val id: Int,
    val title: String,
    var isDone: Boolean
)