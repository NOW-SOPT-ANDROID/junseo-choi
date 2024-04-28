package com.sopt.now.data.remote.service

import com.sopt.now.data.remote.request.SignInRequest
import com.sopt.now.data.remote.request.SignUpRequest
import com.sopt.now.data.remote.response.GetUserResponse
import com.sopt.now.data.remote.response.SignInResponse
import com.sopt.now.data.remote.response.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("member/join")
    suspend fun signUp(
        @Body request: SignUpRequest,
    ): Response<SignUpResponse>

    @POST("member/login")
    suspend fun signIn(
        @Body request: SignInRequest,
    ): Response<SignInResponse>

    @GET("member/info")
    suspend fun getUserInfo(
        @Header("memberId") userId: Int,
    ): Response<GetUserResponse>
}
