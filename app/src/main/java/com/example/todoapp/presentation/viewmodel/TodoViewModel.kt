package com.example.todoapp.presentation.viewmodel

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.model.TodoItem
import com.example.todoapp.domain.usecase.AddTodoUseCases
import com.example.todoapp.domain.usecase.DeleteTodoUseCases
import com.example.todoapp.domain.usecase.GetTodosUseCases
import com.example.todoapp.domain.usecase.UpdateTodoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCases,
    private val addTodoUseCase: AddTodoUseCases,
    private val updateTodoUseCase: UpdateTodoUseCases,
    private val deleteTodoUseCase: DeleteTodoUseCases
) : ViewModel() {
    // The private mutable state that can be updated internally OR Backing property for internal state management
    private val _todoItemList = MutableStateFlow(listOf<TodoItem>())
    // The publicly exposed read-only StateFlow for observation OR Read-only StateFlow exposed to the UI (Activity/Fragment/Compose)
    val todoItemList: StateFlow<List<TodoItem>> = _todoItemList.asStateFlow()
    val todoLabelState = TextFieldState(initialText = "")
    val todoLabelText get() = todoLabelState.text.toString()
    val todos = getTodosUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    init {
        getTodos()
    }
    fun getTodos() {
        viewModelScope.launch {
            getTodosUseCase().collect { todoList ->
                _todoItemList.value = todoList
            }
        }
    }
    fun addTodo(title: String) {
        viewModelScope.launch {
            addTodoUseCase(TodoItem(id = 0, title = title, isDone = false))
        }
    }

    fun updateTodoList(index: Int, isDone: Boolean) {
        viewModelScope.launch {
            _todoItemList.update { currentList ->
                val updatedList = currentList.toMutableList()
                updatedList[index] = updatedList[index].copy(isDone = isDone)
                updatedList.toList()
            }
            updateTodoUseCase(_todoItemList.value[index].id)
        }
    }

    fun deleteTodos() {
        viewModelScope.launch {
            val selectedTodosID = _todoItemList.value.filter { it.isDone }.map { it.id }
            deleteTodoUseCase(selectedTodosID)
        }
    }

    fun deleteTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            deleteTodoUseCase(todoItem)
        }
    }

    fun clearTodoFormFields() {
        todoLabelState.clearText()
    }
}