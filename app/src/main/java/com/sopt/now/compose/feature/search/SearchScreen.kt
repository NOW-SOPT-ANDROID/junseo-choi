package com.sopt.now.compose.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.sopt.now.compose.feature.home.BottomNav

@Composable
fun SearchScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomNav(navController = navController) },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {}
    }
}
