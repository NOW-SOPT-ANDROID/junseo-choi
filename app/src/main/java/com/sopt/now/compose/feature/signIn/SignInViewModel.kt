package com.sopt.now.compose.feature.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.ServicePool
import com.sopt.now.compose.data.remote.request.SignInRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignInViewModel : ViewModel() {
    private val _signInMessage = MutableLiveData<String>()
    val signInMessage: LiveData<String> = _signInMessage

    private val _isWeekSixthHomeworkFinished = MutableLiveData<Boolean>(false)
    val isWeekSixthHomeworkFinished: LiveData<Boolean> = _isWeekSixthHomeworkFinished

    fun performSignIn(request: SignInRequest) {
        viewModelScope.launch {
            runCatching {
                ServicePool.authService.signIn(request)
            }.onSuccess {
                if (it.code() in 200..299) {
                    _signInMessage.value = SUCCESS_SIGN_IN + "/" + it.headers()["LOCATION"]
                } else {
                    _signInMessage.value = it.errorBody()?.string()?.split("\"")?.get(5)
                }
            }.onFailure {
                if (it is HttpException) {
                    _signInMessage.value =
                        it.response()?.errorBody()?.string()?.split("\"")?.get(5)
                }
            }
        }
    }

    fun finishWeekSixthHomework() {
        _isWeekSixthHomeworkFinished.value = true
    }

    companion object {
        const val SUCCESS_SIGN_IN = "SUCCESS"
    }
}
