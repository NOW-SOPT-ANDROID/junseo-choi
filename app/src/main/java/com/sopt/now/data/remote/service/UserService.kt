package com.sopt.now.data.remote.service

import com.sopt.now.data.remote.request.ChangePasswordRequest
import com.sopt.now.data.remote.response.ChangePasswordResponse
import com.sopt.now.data.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH

interface UserService {
    @GET("member/info")
    suspend fun getUserInfo(
        @Header("memberId") userId: Int,
    ): Response<UserResponse>

    @PATCH("member/password")
    suspend fun changePassword(
        @Header("memberId") userId: Int,
        @Body request: ChangePasswordRequest,
    ): Response<ChangePasswordResponse>
}
