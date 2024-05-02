package com.sopt.now.compose.data

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.now.BuildConfig
import com.sopt.now.compose.data.remote.service.AuthService
import com.sopt.now.compose.data.remote.service.FriendService
import com.sopt.now.compose.data.remote.service.UserService
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object NetworkModule {
    private const val CONTENT_TYPE = "application/json"
    private val json: Json =
        Json {
            ignoreUnknownKeys = true
        }

    private fun getLogOkHttpClient(): Interceptor {
        val loggingInterceptor =
            HttpLoggingInterceptor { message ->
                Log.d("Retrofit2", "CONNECTION INFO -> $message")
            }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private val okHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(getLogOkHttpClient())
            .build()

    fun provideRetrofit(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(CONTENT_TYPE.toMediaType()))
            .build()
    }

    inline fun <reified T> create(url: String): T = provideRetrofit(url).create<T>(T::class.java)
}

object ServicePool {
    val authService = NetworkModule.create<AuthService>(BuildConfig.AUTH_BASE_URL)
    val userService = NetworkModule.create<UserService>(BuildConfig.AUTH_BASE_URL)
    val friendService = NetworkModule.create<FriendService>(BuildConfig.FRIEND_BASE_URL)
}
