package com.example.todoapp.presentation.ui

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import com.example.todoapp.util.DeviceConfiguration

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun TodoMainScreen(navigateToAddTodoScreen: () -> Unit) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowClassSize(windowSizeClass)
    when (deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT,
        DeviceConfiguration.TABLET_PORTRAIT -> {
            TodoListScreen(navigateToAddTodoScreen = navigateToAddTodoScreen)
        }
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            ListDetailPaneScaffold(
                directive = navigator.scaffoldDirective,
                value = navigator.scaffoldValue,
                listPane = {
                    AnimatedPane {
                        TodoListScreen()
                    }
                },
                detailPane = {
                    AnimatedPane {
                        AddTodoScreen(
                            showBack = false
                        )
                    }
                }
            )
        }
    }

}