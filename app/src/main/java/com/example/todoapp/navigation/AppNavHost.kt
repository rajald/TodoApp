package com.example.todoapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoapp.presentation.ui.AddTodoScreen
import com.example.todoapp.presentation.ui.TodoScreen

const val SCREEN_TRANSITION_MILLIS = 500

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = NavigationItem.TodoScreen.route
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }
        /*enterTransition = { slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Start, tween(
                SCREEN_TRANSITION_MILLIS
            )
        ) },
        exitTransition = { slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Start, tween(
                SCREEN_TRANSITION_MILLIS
            )
        ) },
        popEnterTransition = { slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.End, tween(
                SCREEN_TRANSITION_MILLIS
            )
        ) },
        popExitTransition = { slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.End, tween(
                SCREEN_TRANSITION_MILLIS
            )
        ) }*/
    ) {
        composable(NavigationItem.TodoScreen.route) {
            TodoScreen(
                navigateToAddTodoScreen = {
                    navController.navigate(NavigationItem.AddTodoScreen.route)
                }
            )
        }
        composable(NavigationItem.AddTodoScreen.route) {
            AddTodoScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}