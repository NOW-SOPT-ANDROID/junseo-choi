package com.sopt.now.compose.feature.main

sealed class Graph(val route: String) {
    data object Auth : Graph("auth")

    data object Main : Graph("main")
}
