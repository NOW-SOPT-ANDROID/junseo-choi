package com.sopt.now.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.remote.repository.FriendRepository
import com.sopt.now.data.remote.repository.UserRepository
import com.sopt.now.data.remote.response.GetFriendsResponse
import com.sopt.now.data.remote.response.GetUserResponse
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
        private val _userInfo = MutableLiveData<GetUserResponse.User>()
        val userInfo: LiveData<GetUserResponse.User> = _userInfo

        private val _friendsInfo = MutableLiveData<List<GetFriendsResponse.Data>>()
        val friendsInfo: LiveData<List<GetFriendsResponse.Data>> = _friendsInfo

        fun getUserInfo(userId: Int) {
            if (userId == 0) {
                _userInfo.value = GetUserResponse.User.defaultUser
                return
            }

            viewModelScope.launch {
                runCatching {
                    userRepository.getUserInfo(userId)
                }.onSuccess {
                    _userInfo.value = it.body()?.data ?: GetUserResponse.User.defaultUser
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

        companion object {
            private const val DEFAULT_FRIEND_PAGE = 1
        }
    }
