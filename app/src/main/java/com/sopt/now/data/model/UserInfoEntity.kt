package com.sopt.now.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserInfoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val password: String,
    val nickname: String,
    val drinkCapacity: Float,
) {
    companion object {
        val defaultUserInfo =
            UserInfoEntity(
                username = "",
                password = "",
                nickname = "",
                drinkCapacity = 0f,
            )
    }
}
