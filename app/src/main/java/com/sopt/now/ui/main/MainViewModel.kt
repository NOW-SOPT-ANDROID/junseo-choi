package com.sopt.now.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.model.UserInfoEntity
import com.sopt.now.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _userInfo = MutableLiveData<UserInfoEntity>(UserInfoEntity.defaultUserInfo)
    val userInfo: LiveData<UserInfoEntity> = _userInfo

    val tempProfileImageUrl: String = "https://avatars.githubusercontent.com/u/127238018?v=4"
    val tempProfileBackgroundImageUrl: String =
        "https://pbs.twimg.com/media/GJsSN8hagAMUyak?format=jpg&name=large"

    fun getUserInfo(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                userRepository.getUserInfo(username)
            }.onSuccess { userInfo ->
                _userInfo.postValue(userInfo)
            }.onFailure {
                Log.e("MainViewModel", "Failed to get user info", it)
            }
        }
    }
}
