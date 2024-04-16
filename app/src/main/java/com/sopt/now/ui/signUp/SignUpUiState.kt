package com.sopt.now.ui.signUp

sealed class SignUpUiState {
    data object Loading : SignUpUiState()

    data object Success : SignUpUiState()

    data object Failure : SignUpUiState()

    data object UsernameError : SignUpUiState()

    data object PasswordError : SignUpUiState()

    data object UsernameBlank : SignUpUiState()

    data object PasswordBlank : SignUpUiState()

    data object NicknameBlank : SignUpUiState()

    data object UsernameTaken : SignUpUiState()

    data object NicknameTaken : SignUpUiState()
}
