package com.sopt.now.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.repository.UserRepository
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _uiState = MutableLiveData<SignUpUiState>(SignUpUiState.Loading)
    val uiState: LiveData<SignUpUiState> = _uiState

    fun checkIsInputValidAndSignUp(
        username: String,
        password: String,
        nickname: String,
        drinkCapacity: Float,
    ) {
        viewModelScope.launch {
            when {
                isUsernameBlank(username) -> return@launch
                isPasswordBlank(password) -> return@launch
                isNicknameBlank(nickname) -> return@launch
                isUsernameValid(username) -> return@launch
                isPasswordValid(password) -> return@launch
                isUsernameTaken(username) -> return@launch
                isNicknameTaken(nickname) -> return@launch
                else -> registerUser(username, password, nickname, drinkCapacity)
            }
        }
    }

    private fun isUsernameBlank(username: String): Boolean {
        if (username.isBlank()) _uiState.value = SignUpUiState.UsernameError
        return _uiState.value == SignUpUiState.UsernameError
    }

    private fun isPasswordBlank(password: String): Boolean {
        if (password.isBlank()) _uiState.value = SignUpUiState.PasswordError
        return _uiState.value == SignUpUiState.PasswordError
    }

    private fun isNicknameBlank(nickname: String): Boolean {
        if (nickname.isBlank()) _uiState.value = SignUpUiState.NicknameBlank
        return _uiState.value == SignUpUiState.NicknameBlank
    }

    private fun isUsernameValid(username: String): Boolean {
        if (username.length !in MIN_USERNAME_LENGTH..MAX_USERNAME_LENGTH) {
            _uiState.value = SignUpUiState.UsernameError
        }
        return _uiState.value == SignUpUiState.UsernameError
    }

    private fun isPasswordValid(password: String): Boolean {
        if (password.length !in MIN_PASSWORD_LENGTH..MAX_PASSWORD_LENGTH) {
            _uiState.value = SignUpUiState.PasswordError
        }
        return _uiState.value == SignUpUiState.PasswordError
    }

    private suspend fun isUsernameTaken(username: String): Boolean {
        val count =
            runCatching {
                userRepository.countUsername(username)
            }.onSuccess {
                if (it != 0) _uiState.postValue(SignUpUiState.UsernameTaken)
            }.onFailure {
                _uiState.postValue(SignUpUiState.Failure)
            }.getOrDefault(0)

        return count != 0 && _uiState.value != SignUpUiState.Failure
    }

    private suspend fun isNicknameTaken(nickname: String): Boolean {
        val count =
            runCatching {
                userRepository.countNickname(nickname)
            }.onSuccess {
                if (it != 0) _uiState.postValue(SignUpUiState.NicknameTaken)
            }.onFailure {
                _uiState.postValue(SignUpUiState.Failure)
            }.getOrDefault(0)

        return count != 0 && _uiState.value != SignUpUiState.Failure
    }

    private fun registerUser(
        username: String,
        password: String,
        nickname: String,
        drinkCapacity: Float,
    ) {
        viewModelScope.launch {
            runCatching {
                userRepository.insertUser(username, password, nickname, drinkCapacity)
            }.onSuccess {
                _uiState.postValue(SignUpUiState.Success)
            }.onFailure {
                _uiState.postValue(SignUpUiState.Failure)
            }
        }
    }

    companion object {
        private const val MIN_USERNAME_LENGTH = 6
        private const val MAX_USERNAME_LENGTH = 10
        private const val MIN_PASSWORD_LENGTH = 8
        private const val MAX_PASSWORD_LENGTH = 12
    }
}
