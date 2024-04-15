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

    fun performSignIn(
        username: String,
        password: String,
    ) {
        if (!checkUsernameBlank(username)) return
        if (!checkPasswordBlank(password)) return

        checkUsernameWrong(username) {
            checkPasswordWrong(username, password) {
                viewModelScope.launch {
                    _uiState.postValue(SignInUiState.Success)
                }
            }
        }
    }

    private fun checkUsernameBlank(username: String): Boolean {
        if (username.isBlank()) {
            _uiState.value = SignInUiState.UsernameBlank
            return false
        }
        return true
    }

    private fun checkPasswordBlank(password: String): Boolean {
        if (password.isBlank()) {
            _uiState.value = SignInUiState.PasswordBlank
            return false
        }
        return true
    }

    private fun checkUsernameWrong(
        username: String,
        onNotWrong: () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                userRepository.countUsername(username)
            }.onSuccess { count ->
                if (count == 0) {
                    _uiState.postValue(SignInUiState.UsernameWrong)
                    return@launch
                }
                onNotWrong()
            }.onFailure {
                _uiState.postValue(SignInUiState.Failure)
            }
        }
    }

    private fun checkPasswordWrong(
        username: String,
        password: String,
        onNotWrong: () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                userRepository.getPasswordByUsername(username)
            }.onSuccess { correctPassword ->
                if (password != correctPassword) {
                    _uiState.postValue(SignInUiState.PasswordWrong)
                    return@launch
                }
                onNotWrong()
            }.onFailure {
                _uiState.postValue(SignInUiState.Failure)
            }
        }
    }
}
