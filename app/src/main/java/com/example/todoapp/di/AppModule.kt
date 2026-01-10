package com.example.todoapp.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.todoapp.BuildConfig
import com.example.todoapp.data.datasource.TodoDao
import com.example.todoapp.data.datasource.TodoDatabase
import com.example.todoapp.data.network.ApiService
import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.data.repository.TodoRepositoryImpl
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://68d55640e29051d1c0ae3ff1.mockapi.io/"

    @Provides
    fun provideRepository(dao: TodoDao, api: ApiService): TodoRepository =
        TodoRepositoryImpl(dao, api)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TodoDatabase =
        Room.databaseBuilder(context, TodoDatabase::class.java, "todo_db").build()

    @Provides
    fun providedDao(db: TodoDatabase) = db.todoDao()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor.Logger { message ->
            val gson = GsonBuilder().setPrettyPrinting().create()
            try {
                val prettyJson = gson.toJson(JsonParser.parseString(message))
                Log.d("TODOApp_Api_JSON:", prettyJson)
            } catch (_: Exception) {
                Log.d("TODOApp_Api", message)
            }
        }
        return HttpLoggingInterceptor(logger).apply {
            // Use Level.BODY for dev, Level.NONE for release
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}