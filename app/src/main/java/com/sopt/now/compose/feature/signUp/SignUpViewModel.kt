package com.sopt.now.compose.feature.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.remote.repository.AuthRepository
import com.sopt.now.compose.data.remote.request.SignUpRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        private val _signUpMessage = MutableLiveData<String>()
        val signUpMessage: LiveData<String> = _signUpMessage

        fun performSignUp(request: SignUpRequest) {
            viewModelScope.launch {
                runCatching {
                    authRepository.signUp(request)
                }.onSuccess {
                    if (it.code() in 200..299) {
                        _signUpMessage.value = SUCCESS_SIGN_UP + "/" + it.headers()["LOCATION"]
                    } else {
                        _signUpMessage.value = it.errorBody()?.string()?.split("\"")?.get(5)
                    }
                }.onFailure {
                    if (it is HttpException) {
                        _signUpMessage.value =
                            it.response()?.errorBody()?.string()?.split("\"")?.get(5)
                    }
                }
            }
        }

        companion object {
            const val SUCCESS_SIGN_UP = "SUCCESS"
        }
    }
