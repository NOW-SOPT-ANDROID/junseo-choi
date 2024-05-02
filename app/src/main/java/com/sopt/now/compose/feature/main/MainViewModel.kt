package com.sopt.now.compose.feature.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.data.remote.response.GetFriendsResponse
import com.sopt.now.compose.data.remote.response.GetUserResponse
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _userInfo = MutableLiveData<GetUserResponse.User>()
    val userInfo: LiveData<GetUserResponse.User> = _userInfo

    private val _friendsInfo = MutableLiveData<List<GetFriendsResponse.Data>>()
    val friendsInfo: LiveData<List<GetFriendsResponse.Data>> = _friendsInfo

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId

    fun getUserInfo(userId: Int) {
        _userId.value = userId

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

    fun getFriendsInfo() {
        viewModelScope.launch {
            runCatching {
                ServicePool.friendService.getFriends(DEFAULT_FRIEND_PAGE)
            }.onSuccess {
                _friendsInfo.value = it.body()?.data ?: emptyList()
            }.onFailure {
                Log.e("MainViewModel", "getUserInfo: $it")
            }
        }
    }

    companion object {
        private const val DEFAULT_FRIEND_PAGE = 1
    }
}
