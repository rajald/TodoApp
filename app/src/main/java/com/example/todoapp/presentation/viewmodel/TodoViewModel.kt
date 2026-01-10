package com.example.todoapp.presentation.viewmodel

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.model.TodoItemData
import com.example.todoapp.domain.model.TodoItemListState
import com.example.todoapp.domain.usecase.AddTodoUseCases
import com.example.todoapp.domain.usecase.DeleteTodoUseCases
import com.example.todoapp.domain.usecase.GetTodosUseCases
import com.example.todoapp.domain.usecase.UpdateTodoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val _todoItemDataListState = MutableStateFlow(TodoItemListState())

    // The publicly exposed read-only StateFlow for observation OR Read-only StateFlow exposed to the UI (Activity/Fragment/Compose)
    val todoItemDataListState: StateFlow<TodoItemListState> = _todoItemDataListState.asStateFlow()
    val todoLabelState = TextFieldState(initialText = "")
    val todoLabelText get() = todoLabelState.text.toString()
   /* val todos = getTodosUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
*/
    fun getTodos() {
        viewModelScope.launch {
            _todoItemDataListState.update { it.copy(isLoading = true) }
            try {
                getTodosUseCase().collect { todoItemState ->
                    _todoItemDataListState.update {
                        it.copy(
                            isLoading = false,
                            todoItemList = todoItemState.todoItemList,
                            errorMessage = todoItemState.errorMessage
                        )
                    }
                }
            } catch (e: Exception) {
                _todoItemDataListState.update {
                    it.copy(isLoading = false, errorMessage = e.message)
                }
            }
        }
    }

    fun addTodo(title: String) {
        viewModelScope.launch {
            addTodoUseCase(TodoItemData(id = 0, book_title = title, isCompleted = false))
        }
    }

    fun updateTodoList(index: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            _todoItemDataListState.update { currentList ->
                val updatedList = currentList.todoItemList.toMutableList()
                updatedList[index] = updatedList[index].copy(isCompleted = isCompleted)
                currentList.copy(
                    todoItemList = updatedList.toList()
                )
            }
            updateTodoUseCase(_todoItemDataListState.value.todoItemList[index])
        }
    }

    fun deleteTodos() {
        viewModelScope.launch {
            val selectedTodosID = _todoItemDataListState.value.todoItemList.filter { it.isCompleted }.map { it.id }
            deleteTodoUseCase(selectedTodosID)
        }
    }

    fun deleteTodoItem(todoItemData: TodoItemData) {
        _todoItemDataListState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            deleteTodoUseCase(todoItemData)
        }
    }

    fun clearTodoFormFields() {
        todoLabelState.clearText()
    }
}