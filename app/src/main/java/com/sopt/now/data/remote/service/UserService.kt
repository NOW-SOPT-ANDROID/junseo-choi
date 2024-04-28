package com.sopt.now.data.remote.service

import com.sopt.now.data.remote.response.GetUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {
    @GET("member/info")
    suspend fun getUserInfo(
        @Header("memberId") userId: Int,
    ): Response<GetUserResponse>
}
