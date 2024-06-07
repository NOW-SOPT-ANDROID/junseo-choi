package com.sopt.now.data.remote.repository

import com.sopt.now.data.remote.request.ChangePasswordRequest
import com.sopt.now.data.remote.response.ChangePasswordResponse
import com.sopt.now.data.remote.response.UserResponse
import com.sopt.now.data.remote.service.UserService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository
    @Inject
    constructor(
        private val userService: UserService,
    ) {
        suspend fun getUserInfo(userId: Int): Response<UserResponse> {
            return userService.getUserInfo(userId)
        }

        suspend fun changePassword(
            userId: Int,
            request: ChangePasswordRequest,
        ): Response<ChangePasswordResponse> {
            return userService.changePassword(userId, request)
        }
    }
