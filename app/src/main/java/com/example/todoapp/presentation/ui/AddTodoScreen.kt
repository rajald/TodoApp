package com.example.todoapp.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.R
import com.example.todoapp.component.AppBar
import com.example.todoapp.component.AppButton
import com.example.todoapp.component.AppTextField
import com.example.todoapp.presentation.viewmodel.TodoViewModel

@Composable
fun AddTodoScreen(onBackClick: () -> Unit) {
    val viewModel: TodoViewModel = hiltViewModel()
    val context = LocalContext.current
    val todoMessageText = stringResource(R.string.msg_todo_added_successfully)

    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.title_add_todo),
                showBack = true,
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            AppButton(
                modifier = Modifier.padding(bottom = 16.dp, start = 24.dp, end = 24.dp),
                label = stringResource(R.string.btn_add_todo),
                onClick = {
                    // Add TodoItem into Database
                    viewModel.addTodo(viewModel.todoLabelText)
                    // Clearing the text
                    viewModel.clearTodoFormFields()
                    Toast.makeText(context, todoMessageText, Toast.LENGTH_SHORT).show()
                },
                isEnabled = viewModel.todoLabelText.isNotEmpty()
            )
        }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                AppTextField(
                    state = viewModel.todoLabelState,
                    placeHolder = stringResource(R.string.hint_enter_todo_name),
                    label = stringResource(R.string.txt_enter_todo),
                )
            }
        }
    }

}