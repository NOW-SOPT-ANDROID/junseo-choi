package com.sopt.now.compose.feature.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.sopt.now.compose.feature.home.HomeScreen
import com.sopt.now.compose.feature.signIn.SignInScreen
import com.sopt.now.compose.feature.signUp.SignUpScreen

@Composable
fun MainScreen(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Graph.Auth.route) {
        authGraph(navController)
        mainGraph(navController)
    }
}

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        startDestination = Screen.SignIn.route,
        route = Graph.Auth.route,
    ) {
        composable(
            route = Screen.SignIn.route,
        ) {
            SignInScreen(navController)
        }

        composable(
            route = Screen.SignUp.route,
        ) {
            SignUpScreen(navController = navController)
        }
    }
}

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(
        startDestination = Screen.Home.route,
        route = Graph.Main.route,
    ) {
        composable(
            route = Screen.Home.route + "/{username}",
            arguments =
                listOf(
                    navArgument("username") { type = NavType.StringType },
                ),
        ) {
            HomeScreen(navController = navController, username = it.arguments?.getString("username").orEmpty())
        }
    }
}
