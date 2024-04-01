package com.sopt.now.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sopt.now.data.model.UserInfoEntity

@Dao
interface UserDao {
    @Query("SELECT username FROM user_table")
    fun getUserNameList(): List<String>

    @Query("SELECT password FROM user_table WHERE username = :userInfo")
    fun getPasswordByUsername(userInfo: String): String

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertUser(userInfoEntity: UserInfoEntity)
}