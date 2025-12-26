package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.datasource.TodoDao
import com.example.todoapp.data.datasource.TodoDatabase
import com.example.todoapp.data.datasource.TodoRepositoryImpl
import com.example.todoapp.data.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideRepository(dao: TodoDao) : TodoRepository = TodoRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : TodoDatabase =
        Room.databaseBuilder(context, TodoDatabase::class.java, "todo_db").build()

    @Provides
    fun providedDao(db: TodoDatabase) = db.todoDao()
}