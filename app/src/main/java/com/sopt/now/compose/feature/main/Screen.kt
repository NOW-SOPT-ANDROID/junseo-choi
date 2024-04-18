package com.sopt.now.compose.feature.main

sealed class Screen(val route: String) {
    data object SignIn : Screen(route = "signIn")

    data object SignUp : Screen(route = "signUp")

    data object Home : Screen(route = "home")
}
