package com.sopt.now.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.ServicePool
import com.sopt.now.data.remote.response.GetUserResponse
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _userInfo = MutableLiveData<GetUserResponse.User>()
    val userInfo: LiveData<GetUserResponse.User> = _userInfo

    fun getUserInfo(userId: Int) {
        if (userId == 0) {
            _userInfo.value = GetUserResponse.defaultUser
            return
        }

        viewModelScope.launch {
            runCatching {
                ServicePool.userService.getUserInfo(userId)
            }.onSuccess {
                _userInfo.value = it.body()?.data ?: GetUserResponse.defaultUser
            }.onFailure {
                Log.e("MainViewModel", "getUserInfo: $it")
            }
        }
    }
}
