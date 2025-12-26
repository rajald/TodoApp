package com.example.todoapp.presentation.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.R
import com.example.todoapp.component.AppBar
import com.example.todoapp.component.AppButton
import com.example.todoapp.component.AppCheckBox
import com.example.todoapp.presentation.viewmodel.TodoViewModel

@Composable
fun TodoScreen(navigateToAddTodoScreen: () -> Unit) {
    val viewModel: TodoViewModel = hiltViewModel()
    val todoItemList by viewModel.todoItemList.collectAsState()
    val enableDeleteButton = todoItemList.any { it.isDone }
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.title_todo_list)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigateToAddTodoScreen()
                },
                contentColor = MaterialTheme.colorScheme.secondary,
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.btn_add_todo)
                )
            }
        },
        bottomBar = {
            AppButton(
                modifier = Modifier.padding(bottom = 16.dp, start = 24.dp, end = 24.dp),
                label = stringResource(R.string.btn_delete_todos),
                onClick = { viewModel.deleteTodos() },
                isEnabled = enableDeleteButton
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            itemsIndexed(todoItemList) { index, todo ->
                var isChecked by remember { mutableStateOf(todo.isDone) }
                AppCheckBox(
                    label = todo.title,
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = it
                        viewModel.updateTodoList(index = index, isDone = isChecked)
                    },
                    onTrailingIconClick = {
                        viewModel.deleteTodoItem(todo)
                    },
                    showTrailingIcon = true
                )
                if (index == todoItemList.size.minus(1)) {
                    Spacer(modifier = Modifier.height(72.dp))
                }
            }
        }
    }
}