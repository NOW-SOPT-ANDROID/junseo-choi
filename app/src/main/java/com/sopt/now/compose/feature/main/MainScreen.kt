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
import com.sopt.now.compose.feature.myPage.MyPageScreen
import com.sopt.now.compose.feature.search.SearchScreen
import com.sopt.now.compose.feature.signIn.SignInScreen
import com.sopt.now.compose.feature.signUp.SignUpScreen
import com.sopt.now.compose.model.Graph
import com.sopt.now.compose.model.Screen

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
            route = Screen.MyPage.route + "/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType }),
        ) {
            MyPageScreen(navController = navController)
        }
        composable(
            route = Screen.Home.route + "/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType }),
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Search.route,
        ) {
            SearchScreen(navController = navController)
        }
    }
}