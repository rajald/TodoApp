package com.example.todoapp.domain.model

data class TodoItemListState (
    val isLoading: Boolean = false,
    val todoItemList: List<TodoItemData> = emptyList(),
    val errorMessage: String? = null
)