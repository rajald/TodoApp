package com.example.todoapp.data.network

import com.example.todoapp.domain.model.TodoItemData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("books")
    suspend fun getTodoTasks(): Response<List<TodoItemData>>

    @POST("books")
    suspend fun addTodoTasks(@Body todoItemData: TodoItemData): Response<TodoItemData>

    @DELETE("books/{id}")
    suspend fun deleteTodoTask(@Path("id") taskId: String): Response<TodoItemData>

    @PUT("books/{id}")
    suspend fun updateCompleteStatus(
        @Path("id") taskId: String,
        @Body todoItemData: TodoItemData
    ): Response<TodoItemData>

}