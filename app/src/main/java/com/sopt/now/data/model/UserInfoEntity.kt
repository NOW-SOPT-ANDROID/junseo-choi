package com.sopt.now.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserInfoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val username: String,
    @ColumnInfo val password: String,
    @ColumnInfo val nickname: String,
    @ColumnInfo val drinkCapacity: Float,
)