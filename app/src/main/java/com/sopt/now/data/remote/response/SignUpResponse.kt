package com.sopt.now.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    @SerialName("code")
    val status: Int,
    @SerialName("message")
    val message: String,
)
