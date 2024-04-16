package com.sopt.now.ui.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _uiState = MutableLiveData<SignInUiState>(SignInUiState.Loading)
    val uiState: LiveData<SignInUiState> = _uiState

    fun checkIsInputValidAndSignIn(
        username: String,
        password: String,
    ) {
        if (checkUsernameBlank(username)) return
        if (checkPasswordBlank(password)) return
        if (checkIsInputDataWrong(username, password)) return

        performSignIn()
    }

    private fun checkIsInputDataWrong(
        username: String,
        password: String,
    ): Boolean {
        if (checkUsernameWrong(username)) return true
        if (checkPasswordWrong(username, password)) return true
        return false
    }

    private fun performSignIn() {
        viewModelScope.launch {
            _uiState.postValue(SignInUiState.Success)
        }
    }

    private fun checkUsernameBlank(username: String): Boolean {
        if (username.isBlank()) _uiState.value = SignInUiState.UsernameBlank
        return _uiState.value == SignInUiState.UsernameBlank
    }

    private fun checkPasswordBlank(password: String): Boolean {
        if (password.isBlank()) _uiState.value = SignInUiState.PasswordBlank
        return _uiState.value == SignInUiState.PasswordBlank
    }

    private fun checkUsernameWrong(username: String): Boolean {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                userRepository.countUsername(username)
            }.onSuccess { count ->
                if (count == 0) _uiState.postValue(SignInUiState.UsernameWrong)
            }.onFailure {
                _uiState.postValue(SignInUiState.Failure)
            }
        }
        return _uiState.value == SignInUiState.UsernameWrong && _uiState.value != SignInUiState.Failure
    }

    private fun checkPasswordWrong(
        username: String,
        password: String,
    ): Boolean {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                userRepository.getPasswordByUsername(username)
            }.onSuccess { correctPassword ->
                if (password != correctPassword) _uiState.postValue(SignInUiState.PasswordWrong)
            }.onFailure {
                _uiState.postValue(SignInUiState.Failure)
            }
        }
        return _uiState.value == SignInUiState.PasswordWrong && _uiState.value != SignInUiState.Failure
    }
}
