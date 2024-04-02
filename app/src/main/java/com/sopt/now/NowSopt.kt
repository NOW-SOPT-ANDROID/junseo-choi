package com.sopt.now

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.sopt.now.data.local.dao.UserInfoDao
import com.sopt.now.data.local.database.UserDatabase
import com.sopt.now.data.repository.UserRepository

class NowSopt: Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    companion object {
        lateinit var appContext: Context
            private set

        fun getUserRepository(): UserRepository {
            val userInfoDao: UserInfoDao = UserDatabase.getInstance(appContext).userInfoDao()

            return UserRepository(userInfoDao)
        }
    }
}