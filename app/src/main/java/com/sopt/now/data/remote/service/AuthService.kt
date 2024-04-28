package com.sopt.now.data.remote.service

import com.sopt.now.data.remote.request.SignUpRequest
import com.sopt.now.data.remote.response.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("member/join")
    suspend fun signUp(
        @Body request: SignUpRequest,
    ): Response<SignUpResponse>
}
