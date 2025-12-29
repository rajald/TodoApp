package com.example.todoapp.navigation

sealed class NavigationItem (val route: String) {
    object TodoMainScreen: NavigationItem(ScreenRoutes.TODO_MAIN_SCREEN)
    object TodoListScreen : NavigationItem(ScreenRoutes.TODO_LIST_SCREEN)
    object AddTodoScreen: NavigationItem(ScreenRoutes.ADD_TODO_SCREEN)
}