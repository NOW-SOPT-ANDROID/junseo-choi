package com.sopt.now.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.ServicePool
import com.sopt.now.data.remote.request.SignUpRequest
import com.sopt.now.data.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _signUpMessage = MutableLiveData<String>()
    val signUpMessage: LiveData<String> = _signUpMessage

    fun performSignUp(request: SignUpRequest) {
        viewModelScope.launch {
            runCatching {
                ServicePool.authService.signUp(request)
            }.onSuccess {
                _signUpMessage.value = it.message
            }.onFailure {
                if (it is HttpException) {
                    _signUpMessage.value =
                        it.response()?.errorBody()?.string()?.split("\"")?.get(5)
                }
            }
        }
    }
}
