package com.sopt.now.data

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.now.BuildConfig
import com.sopt.now.data.remote.service.AuthService
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object NetworkModule {
    private const val AUTH_BASE_URL = BuildConfig.AUTH_BASE_URL

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

    val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(AUTH_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(CONTENT_TYPE.toMediaType()))
            .build()

    inline fun <reified T> create(): T = retrofit.create<T>(T::class.java)
}

object ServicePool {
    val authService = NetworkModule.create<AuthService>()
}
