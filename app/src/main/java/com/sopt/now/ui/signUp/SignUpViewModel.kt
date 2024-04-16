package com.sopt.now.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
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
        if (checkUsernameValid(username)) return
        if (checkPasswordValid(password)) return
        if (checkNicknameBlank(nickname)) return
        if (checkUsernameTaken(username)) return
        if (checkNicknameTaken(nickname)) return

        registerUser(username, password, nickname, drinkCapacity)
    }

    private fun checkUsernameValid(username: String): Boolean {
        if (username.length !in MIN_USERNAME_LENGTH..MAX_USERNAME_LENGTH) {
            _uiState.value =
                SignUpUiState.UsernameError
        }
        return _uiState.value == SignUpUiState.UsernameError
    }

    private fun checkPasswordValid(password: String): Boolean {
        if (password.length !in MIN_PASSWORD_LENGTH..MAX_PASSWORD_LENGTH) {
            _uiState.value =
                SignUpUiState.PasswordError
        }
        return _uiState.value == SignUpUiState.PasswordError
    }

    private fun checkNicknameBlank(nickname: String): Boolean {
        if (nickname.isBlank()) _uiState.value = SignUpUiState.NicknameError
        return _uiState.value == SignUpUiState.NicknameError
    }

    private fun checkUsernameTaken(username: String): Boolean {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                userRepository.countUsername(username)
            }.onSuccess { count ->
                if (count != 0) {
                    _uiState.postValue(SignUpUiState.UsernameTaken)
                }
            }.onFailure {
                _uiState.postValue(SignUpUiState.Failure)
            }
        }
        return _uiState.value == SignUpUiState.UsernameTaken && _uiState.value != SignUpUiState.Failure
    }

    private fun checkNicknameTaken(nickname: String): Boolean {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                userRepository.countNickname(nickname)
            }.onSuccess { count ->
                if (count != 0) {
                    _uiState.postValue(SignUpUiState.NicknameTaken)
                }
            }.onFailure {
                _uiState.postValue(SignUpUiState.Failure)
            }
        }
        return _uiState.value == SignUpUiState.NicknameTaken && _uiState.value != SignUpUiState.Failure
    }

    private fun registerUser(
        username: String,
        password: String,
        nickname: String,
        drinkCapacity: Float,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
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
