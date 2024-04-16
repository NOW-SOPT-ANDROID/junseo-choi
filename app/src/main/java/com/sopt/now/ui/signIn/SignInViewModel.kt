package com.sopt.now.ui.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.repository.UserRepository
import kotlinx.coroutines.launch

class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _uiState = MutableLiveData<SignInUiState>(SignInUiState.Loading)
    val uiState: LiveData<SignInUiState> = _uiState

    fun checkIsInputValidAndSignIn(
        username: String,
        password: String,
    ) {
        viewModelScope.launch {
            when {
                checkUsernameBlank(username) -> return@launch
                checkPasswordBlank(password) -> return@launch
                checkUsernameWrong(username) -> return@launch
                checkPasswordWrong(username, password) -> return@launch
                else -> performSignIn()
            }
        }
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

    private suspend fun checkUsernameWrong(username: String): Boolean {
        val count =
            runCatching {
                userRepository.countUsername(username)
            }.onSuccess { count ->
                if (count == 0) _uiState.postValue(SignInUiState.UsernameWrong)
            }.onFailure {
                _uiState.postValue(SignInUiState.Failure)
            }.getOrNull() ?: 0

        return count == 0 && _uiState.value != SignInUiState.Failure
    }

    private suspend fun checkPasswordWrong(
        username: String,
        password: String,
    ): Boolean {
        val correctPassword =
            runCatching {
                userRepository.getPasswordByUsername(username)
            }.onSuccess { correctPassword ->
                if (password != correctPassword) _uiState.postValue(SignInUiState.PasswordWrong)
            }.onFailure {
                _uiState.postValue(SignInUiState.Failure)
            }.getOrNull()

        return password != correctPassword && _uiState.value != SignInUiState.Failure
    }
}
