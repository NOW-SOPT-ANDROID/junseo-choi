package com.sopt.now.compose.feature.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.remote.repository.FriendRepository
import com.sopt.now.compose.data.remote.repository.UserRepository
import com.sopt.now.compose.data.remote.response.FriendsResponse
import com.sopt.now.compose.data.remote.response.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
        private val friendRepository: FriendRepository,
    ) : ViewModel() {
        private val _userInfo = MutableLiveData<UserResponse.User>()
        val userInfo: LiveData<UserResponse.User> = _userInfo

        private val _friendsInfo = MutableLiveData<List<FriendsResponse.Data>>()
        val friendsInfo: LiveData<List<FriendsResponse.Data>> = _friendsInfo

        private val _userId = MutableLiveData<Int>()
        val userId: LiveData<Int> = _userId

        fun getUserInfo(userId: Int) {
            if (userId == 0) {
                _userInfo.value = UserResponse.User.defaultUser
                return
            }

            viewModelScope.launch {
                runCatching {
                    userRepository.getUserInfo(userId)
                }.onSuccess {
                    _userInfo.value = it.body()?.data ?: UserResponse.User.defaultUser
                    _userId.value = userId
                }.onFailure {
                    Log.e("MainViewModel", "getUserInfo: $it")
                }
            }
        }

        fun getFriendsInfo() {
            viewModelScope.launch {
                runCatching {
                    friendRepository.getFriends(DEFAULT_FRIEND_PAGE)
                }.onSuccess {
                    _friendsInfo.value = it.body()?.data ?: emptyList()
                }.onFailure {
                    Log.e("MainViewModel", "getUserInfo: $it")
                }
            }
        }

        fun setUserId(userId: Int) {
            _userId.value = userId
        }

        companion object {
            private const val DEFAULT_FRIEND_PAGE = 1
        }
    }
