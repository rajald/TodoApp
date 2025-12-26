package com.example.todoapp.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = false
) {
    Button(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick,
        enabled = isEnabled
    ) {
        Text(
            text = label
        )
    }
}