package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.datasource.TodoDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        TodoDatabase.getInstance(applicationContext)
    }
}