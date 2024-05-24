package com.sopt.now.compose.feature.myPage

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sopt.now.compose.R
import com.sopt.now.compose.data.remote.response.UserResponse
import com.sopt.now.compose.feature.common.base.BaseFactory
import com.sopt.now.compose.feature.home.BottomNav
import com.sopt.now.compose.feature.main.MainViewModel
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

@Composable
fun MyPageScreen(navController: NavController) {
    val context = LocalContext.current
    val mainViewModel = provideMainViewModel(context)
    val userInfo = mainViewModel.userInfo.observeAsState(UserResponse.User.defaultUser).value

    Scaffold(bottomBar = { BottomNav(navController = navController) }) { paddingValues ->
        UserInfoDisplay(modifier = Modifier.padding(paddingValues), userInfo = userInfo)
    }
}

@Composable
fun UserInfoDisplay(
    modifier: Modifier = Modifier,
    userInfo: UserResponse.User,
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier.padding(24.dp)) {
            UserProfileImage(url = "https://avatars.githubusercontent.com/u/127238018?v=4")
            UserInfoText(label = R.string.nickname_label, value = userInfo.nickname, topPadding = 40.dp)
            UserInfoText(label = R.string.username_label, value = userInfo.authenticationId)
            UserInfoText(label = R.string.phone_number_label, value = userInfo.phone)
        }
    }
}

@Composable
fun UserProfileImage(
    url: String,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model =
            ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
        contentDescription = "Profile Picture",
        contentScale = ContentScale.Crop,
        modifier = modifier.size(200.dp),
    )
}

@Composable
fun UserInfoText(
    @androidx.annotation.StringRes label: Int,
    value: String,
    topPadding: Dp = 16.dp,
) {
    Text(
        text = stringResource(id = label),
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.purple_500),
        modifier = Modifier.padding(top = topPadding),
    )
    Text(
        text = value,
        fontSize = 16.sp,
        modifier = Modifier.padding(top = 4.dp),
    )
}

@Composable
fun provideMainViewModel(context: Context): MainViewModel {
    val factory = BaseFactory { MainViewModel() }
    return ViewModelProvider(
        context as ComponentActivity,
        factory,
    )[MainViewModel::class.java]
}

@Preview(showBackground = true)
@Composable
fun MyPagePreview() {
    NOWSOPTAndroidTheme {
        MyPageScreen(navController = NavController(LocalContext.current))
    }
}
