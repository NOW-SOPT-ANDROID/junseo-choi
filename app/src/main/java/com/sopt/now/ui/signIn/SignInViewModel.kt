package com.sopt.now.ui.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.ServicePool
import com.sopt.now.data.remote.request.SignInRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignInViewModel : ViewModel() {
    private val _signInMessage = MutableLiveData<String>()
    val signInMessage: LiveData<String> = _signInMessage

    fun performSignIn(request: SignInRequest) {
        viewModelScope.launch {
            runCatching {
                ServicePool.authService.signIn(request)
            }.onSuccess {
                _signInMessage.value = SUCCESS_SIGN_IN + "/" + it.headers()["LOCATION"]
            }.onFailure {
                if (it is HttpException) {
                    _signInMessage.value =
                        it.response()?.errorBody()?.string()?.split("\"")?.get(5)
                }
            }
        }
    }

    companion object {
        const val SUCCESS_SIGN_IN = "SUCCESS"
    }
}
