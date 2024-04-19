package com.sopt.now.compose.model

sealed class Graph(val route: String) {
    data object Auth : Graph("auth")

    data object Main : Graph("main")
}
