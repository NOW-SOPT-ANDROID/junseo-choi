package com.sopt.now.compose.feature.home

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sopt.now.compose.model.BottomNavItem

@Composable
fun BottomNav(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items =
        listOf<BottomNavItem>(
            BottomNavItem.Search,
            BottomNavItem.Home,
            BottomNavItem.MyPage,
        )
    NavigationBar(modifier = Modifier.height(120.dp)) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(item.icon),
                        contentDescription = "icon",
                    )
                },
                label = { Text(stringResource(id = item.title)) },
                selected = currentRoute == item.route,
                onClick = {
                    val username = if (item.title == BottomNavItem.Search.title) "" else "/prime54"
                    navController.navigate(item.route + username) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}
