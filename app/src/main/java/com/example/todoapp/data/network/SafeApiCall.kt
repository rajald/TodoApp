package com.example.todoapp.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T : Any> safeApiCall(
    execute: suspend () -> Response<T>
): NetworkResult<T?> {
    return try {
        //Execute background operation on IO thread
        withContext(Dispatchers.IO) {
            val response = execute()
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body())
            } else {
                // Throwing an exception here allows Result to capture it in the catch block
                NetworkResult.Error(response.code(), "${response.message()}")
                //throw Exception("Error Code: ${response.code()}, Error Message: ${response.message()}")
            }
        }
    } catch (e: Exception) {
        NetworkResult.Exception(e)
    }
}