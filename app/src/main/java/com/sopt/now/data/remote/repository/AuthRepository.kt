package com.sopt.now.data.remote.repository

import com.sopt.now.data.remote.request.SignInRequest
import com.sopt.now.data.remote.request.SignUpRequest
import com.sopt.now.data.remote.response.SignInResponse
import com.sopt.now.data.remote.response.SignUpResponse
import com.sopt.now.data.remote.service.AuthService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository
    @Inject
    constructor(
        private val authService: AuthService,
    ) {
        suspend fun signUp(request: SignUpRequest): Response<SignUpResponse> {
            return authService.signUp(request)
        }

        suspend fun signIn(request: SignInRequest): Response<SignInResponse> {
            return authService.signIn(request)
        }
    }
