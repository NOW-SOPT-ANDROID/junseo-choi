package com.sopt.now.compose.model

import com.sopt.now.compose.R

sealed class BottomNavItem(
    val title: Int,
    var icon: Int,
    val route: String,
) {
    data object Home : BottomNavItem(
        title = R.string.main_home,
        icon = R.drawable.ic_main_home,
        route = Screen.Home.route,
    )

    data object MyPage : BottomNavItem(
        title = R.string.main_my_page,
        icon = R.drawable.ic_main_my_page,
        route = Screen.MyPage.route,
    )

    data object Search : BottomNavItem(
        title = R.string.main_search,
        icon = R.drawable.ic_main_search,
        route = Screen.Search.route,
    )
}
