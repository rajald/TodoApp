package com.example.todoapp.navigation

sealed class NavigationItem (val route: String) {
    object TodoScreen : NavigationItem(ScreenRoutes.TODO_SCREEN)
    object AddTodoScreen: NavigationItem(ScreenRoutes.ADD_TODO_SCREEN)
}