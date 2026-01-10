package com.example.todoapp.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todoapp.R
import com.example.todoapp.design_system.component.TodoAppBar
import com.example.todoapp.design_system.component.TodoButton
import com.example.todoapp.design_system.component.TodoCheckBox
import com.example.todoapp.design_system.component.TodoCircularProgressBar
import com.example.todoapp.design_system.component.TodoErrorMessage
import com.example.todoapp.presentation.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun TodoListScreen(navigateToAddTodoScreen: (() -> Unit)? = null) {
    val viewModel: TodoViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.getTodos()
    }

    val todoItemDataListState by viewModel.todoItemDataListState.collectAsStateWithLifecycle()
    val enableDeleteButton = todoItemDataListState.todoItemList.any { it.isCompleted }
    Scaffold(
        topBar = {
            TodoAppBar(
                title = stringResource(R.string.title_todo_list)
            )
        },
        floatingActionButton = {
            if (navigateToAddTodoScreen != null) {
                FloatingActionButton(
                    onClick = {
                        navigateToAddTodoScreen()
                    },
                    contentColor = MaterialTheme.colorScheme.secondary,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.btn_add_todo)
                    )
                }
            }
        },
        bottomBar = {
            if (!todoItemDataListState.isLoading && todoItemDataListState.todoItemList.isNotEmpty()) {
                TodoButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, start = 24.dp, end = 24.dp),
                    text = stringResource(R.string.btn_delete_todos),
                    onClick = { viewModel.deleteTodos() },
                    isEnabled = enableDeleteButton
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        TodoList(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Composable
fun TodoList(modifier: Modifier) {
    val viewModel: TodoViewModel = hiltViewModel()
    val todoItemListState by viewModel.todoItemDataListState.collectAsStateWithLifecycle()
    Box(modifier = modifier) {
        when {
            todoItemListState.isLoading -> {
                // Show a loading indicator
                TodoCircularProgressBar()
            }

            todoItemListState.todoItemList.isEmpty() -> {
                // Show the "No data found" message, only visible if the list is empty
                NoDataFoundMessage()
            }

            else -> {
                // Show the actual list data
                // Show data once loading is complete
                Column {
                    if (todoItemListState.errorMessage.isNullOrEmpty().not()) {
                        // Show an error message
                        TodoErrorMessage(
                            errorMessage = "${todoItemListState.errorMessage}"
                        )
                    }
                    LazyColumn {
                        itemsIndexed(
                            items = todoItemListState.todoItemList,
                        ) { index, todo ->
                            var isChecked by remember { mutableStateOf(todo.isCompleted) }
                            TodoCheckBox(
                                label = todo.book_title,
                                checked = isChecked,
                                onCheckedChange = {
                                    isChecked = it
                                    viewModel.updateTodoList(index = index, isCompleted = isChecked)
                                },
                                onTrailingIconClick = {
                                    viewModel.deleteTodoItem(todo)
                                },
                                showTrailingIcon = true
                            )
                            if (index == todoItemListState.todoItemList.size.minus(1)) {
                                Spacer(modifier = Modifier.height(72.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NoDataFoundMessage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "No Data",
            tint = Color.Gray,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "No Todo Item Exist",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Add todo items",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.LightGray
        )
    }
}