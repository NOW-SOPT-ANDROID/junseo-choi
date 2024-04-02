package com.sopt.now.data.repository

import com.sopt.now.data.local.dao.UserInfoDao
import com.sopt.now.data.model.UserInfoEntity

class UserRepository(private val userInfoDao: UserInfoDao) {

    fun getPasswordByUsername(username: String): String {
        return userInfoDao.getPasswordByUsername(username)
    }

    fun insertUser(username: String, password: String, nickname: String, drinkCapacity: Int) {
        userInfoDao.insertUser(UserInfoEntity(username = username, password = password, nickname = nickname, drinkCapacity = drinkCapacity))
    }

    fun countUsername(username: String): Int {
        return userInfoDao.countUsername(username)
    }

    fun countNickname(nickname: String): Int {
        return userInfoDao.countNickname(nickname)
    }

}