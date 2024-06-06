package com.sopt.now.ui.passwordChange

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.remote.repository.UserRepository
import com.sopt.now.data.remote.request.ChangePasswordRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class PasswordChangeViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val _passwordChangeMessage = MutableLiveData<String>()
        val passwordChangeMessage: LiveData<String> = _passwordChangeMessage

        fun performPasswordChange(
            userId: Int,
            request: ChangePasswordRequest,
        ) {
            viewModelScope.launch {
                runCatching {
                    userRepository.changePassword(userId, request)
                }.onSuccess {
                    if (it.code() in 200..299) {
                        _passwordChangeMessage.value = it.body()?.message
                    } else {
                        _passwordChangeMessage.value = it.errorBody()?.string()?.split("\"")?.get(5)
                    }
                }.onFailure {
                    if (it is HttpException) {
                        _passwordChangeMessage.value =
                            it.response()?.errorBody()?.string()?.split("\"")?.get(5)
                    }
                }
            }
        }

        companion object {
            const val SUCCESS_PASSWORD_CHANGE = "비밀번호 변경이 완료되었습니다."
        }
    }
