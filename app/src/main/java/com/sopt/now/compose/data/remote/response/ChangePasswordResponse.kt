package com.sopt.now.compose.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordResponse(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
)
