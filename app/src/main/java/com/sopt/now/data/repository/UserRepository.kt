package com.sopt.now.data.repository

import com.sopt.now.data.local.dao.UserInfoDao
import com.sopt.now.data.model.UserInfoEntity

class UserRepository(private val userInfoDao: UserInfoDao) {
    suspend fun getPasswordByUsername(username: String): String {
        return userInfoDao.getPasswordByUsername(username)
    }

    suspend fun insertUser(
        username: String,
        password: String,
        nickname: String,
        drinkCapacity: Float,
    ) {
        userInfoDao.insertUser(
            UserInfoEntity(
                username = username,
                password = password,
                nickname = nickname,
                drinkCapacity = drinkCapacity,
            ),
        )
    }

    suspend fun countUsername(username: String): Int {
        return userInfoDao.countUsername(username)
    }

    suspend fun countNickname(nickname: String): Int {
        return userInfoDao.countNickname(nickname)
    }

    suspend fun getUserInfo(username: String): UserInfoEntity {
        return userInfoDao.getUserInfo(username)
    }
}
