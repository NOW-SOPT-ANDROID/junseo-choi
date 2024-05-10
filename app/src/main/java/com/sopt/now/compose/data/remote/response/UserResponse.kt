package com.sopt.now.compose.data.remote.response

import com.sopt.now.compose.data.remote.response.UserResponse.User.Companion.defaultUser
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val code: Int,
    val message: String,
    val data: User? = defaultUser,
) {
    @Serializable
    data class User(
        val authenticationId: String,
        val nickname: String,
        val phone: String,
    ) {
        companion object {
            val defaultUser =
                User(
                    authenticationId = "",
                    nickname = "",
                    phone = "",
                )
        }
    }
}
