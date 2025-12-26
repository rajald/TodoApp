package com.example.todoapp.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun AppTextField(
    state: TextFieldState,
    label: String,
    modifier: Modifier = Modifier,
    placeHolder: String = "",
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    outputTransformation: OutputTransformation? = null,
    inputTransformation: InputTransformation? = null
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        state = state,
        label = { Text(text = label) },
        placeholder = { Text(text = placeHolder) },
        lineLimits = if (isSingleLine) {
            TextFieldLineLimits.SingleLine
        } else {
            TextFieldLineLimits.Default
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        outputTransformation = outputTransformation,
        inputTransformation = inputTransformation
    )
}