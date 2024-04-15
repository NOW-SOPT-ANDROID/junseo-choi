package com.sopt.now.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sopt.now.data.model.UserInfoEntity

@Dao
interface UserInfoDao {
    @Query("SELECT password FROM user_table WHERE username = :username")
    suspend fun getPasswordByUsername(username: String): String

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(userInfoEntity: UserInfoEntity)

    @Query("SELECT COUNT(username) FROM user_table WHERE username = :username")
    suspend fun countUsername(username: String): Int

    @Query("SELECT COUNT(nickname) FROM user_table WHERE nickname = :nickname")
    suspend fun countNickname(nickname: String): Int

    @Query("SELECT * FROM user_table WHERE username = :username")
    suspend fun getUserInfo(username: String): UserInfoEntity
}
