package com.sopt.now.compose.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String = "",
    val password: String = "",
    val nickname: String = "",
    val drinkCapacity: Float = 0f,
) : Parcelable
