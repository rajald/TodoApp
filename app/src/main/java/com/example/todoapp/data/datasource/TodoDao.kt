package com.example.todoapp.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos")
    fun getTodos(): Flow<List<TodoEntity>>

    @Insert
    suspend fun insert(todo: TodoEntity)

    @Query("UPDATE todos SET isDone = NOT isDone WHERE id = :id")
    suspend fun toggle(id: Int)

    @Delete
    suspend fun delete(todo: TodoEntity)

    @Query("DELETE FROM todos WHERE id IN (:idList)")
    suspend fun deleteTodos(idList: List<Int>)
}