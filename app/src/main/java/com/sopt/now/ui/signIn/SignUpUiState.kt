package com.sopt.now.ui.signIn

sealed class SignInUiState {
    data object Loading : SignInUiState()
    data object Success : SignInUiState()
    data object Failure : SignInUiState()
    data object UsernameBlank : SignInUiState()
    data object PasswordBlank : SignInUiState()
    data object UsernameWrong : SignInUiState()
    data object PasswordWrong : SignInUiState()
}