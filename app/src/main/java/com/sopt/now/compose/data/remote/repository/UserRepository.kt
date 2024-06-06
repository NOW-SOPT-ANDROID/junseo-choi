package com.sopt.now.compose.data.remote.repository

import com.sopt.now.compose.data.remote.response.UserResponse
import com.sopt.now.compose.data.remote.service.UserService
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
    }
