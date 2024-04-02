package com.sopt.now.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sopt.now.NowSopt
import com.sopt.now.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _uiState = MutableLiveData<SignUpUiState>(SignUpUiState.Loading)
    val uiState: LiveData<SignUpUiState> = _uiState

    fun performSignUp(username: String, password: String, nickname: String, drinkCapacity: Int) {
        if (!checkUsernameValid(username)) return
        if (!checkPasswordValid(password)) return
        if (!checkNicknameNotBlank(nickname)) return

        checkUsernameTaken(username) {
            checkNicknameTaken(nickname) {
                registerUser(username, password, nickname, drinkCapacity)
            }
        }
    }

    private fun checkUsernameValid(username: String): Boolean {
        if (username.length !in MIN_USERNAME_LENGTH..MAX_USERNAME_LENGTH) {
            _uiState.value = SignUpUiState.UsernameError
            return false
        }
        return true
    }

    private fun checkPasswordValid(password: String): Boolean {
        if (password.length !in MIN_PASSWORD_LENGTH..MAX_PASSWORD_LENGTH) {
            _uiState.value = SignUpUiState.PasswordError
            return false
        }
        return true
    }

    private fun checkNicknameNotBlank(nickname: String): Boolean {
        if (nickname.isBlank()) {
            _uiState.value = SignUpUiState.NicknameError
            return false
        }
        return true
    }

    private fun checkUsernameTaken(username: String, onNotTaken: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                userRepository.countUsername(username)
            }.onSuccess { count ->
                if (count != 0) {
                    _uiState.postValue(SignUpUiState.UsernameTaken)
                    return@launch
                }
                onNotTaken()
            }.onFailure {
                _uiState.postValue(SignUpUiState.Failure)
            }
        }
    }

    private fun checkNicknameTaken(nickname: String, onNotTaken: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                userRepository.countNickname(nickname)
            }.onSuccess { count ->
                if (count != 0) {
                    _uiState.postValue(SignUpUiState.NicknameTaken)
                    return@launch
                }
                onNotTaken()
            }.onFailure {
                _uiState.postValue(SignUpUiState.Failure)
            }
        }
    }

    private fun registerUser(
        username: String,
        password: String,
        nickname: String,
        drinkCapacity: Int
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
        const val MIN_USERNAME_LENGTH = 6
        const val MAX_USERNAME_LENGTH = 10
        const val MIN_PASSWORD_LENGTH = 8
        const val MAX_PASSWORD_LENGTH = 12

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return SignUpViewModel(NowSopt.getUserRepository()) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}