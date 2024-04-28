package com.sopt.now.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class GetUserResponse(
    val code: Int,
    val message: String,
    val data: User? = defaultUser,
) {
    @Serializable
    data class User(
        val authenticationId: String,
        val nickname: String,
        val phone: String,
    )

    companion object {
        val defaultUser =
            User(
                authenticationId = "",
                nickname = "",
                phone = "",
            )
    }
}
