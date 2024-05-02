package com.sopt.now.compose.model

sealed class Screen(val route: String) {
    data object SignIn : Screen(route = "signIn")

    data object SignUp : Screen(route = "signUp")

    data object Home : Screen(route = "home")

    data object MyPage : Screen(route = "myPage")

    data object Search : Screen(route = "search")
}
