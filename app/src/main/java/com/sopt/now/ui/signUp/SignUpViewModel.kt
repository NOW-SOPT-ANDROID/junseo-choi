package com.sopt.now.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.ServicePool
import com.sopt.now.data.remote.request.SignUpRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignUpViewModel() : ViewModel() {
    private val _signUpMessage = MutableLiveData<String>()
    val signUpMessage: LiveData<String> = _signUpMessage

    fun performSignUp(request: SignUpRequest) {
        viewModelScope.launch {
            runCatching {
                ServicePool.authService.signUp(request)
            }.onSuccess {
                _signUpMessage.value = SUCCESS_SIGN_UP + "/" + it.headers()["LOCATION"]
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
