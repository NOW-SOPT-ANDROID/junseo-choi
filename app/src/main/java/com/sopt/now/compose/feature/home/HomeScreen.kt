package com.sopt.now.compose.feature.home

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sopt.now.compose.data.remote.response.FriendsResponse
import com.sopt.now.compose.data.remote.response.UserResponse
import com.sopt.now.compose.feature.common.base.BaseFactory
import com.sopt.now.compose.feature.main.MainViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current

    val mainViewModel =
        ViewModelProvider(
            context as ComponentActivity,
            BaseFactory { MainViewModel() },
        )[MainViewModel::class.java]

    val userInfo = mainViewModel.userInfo.observeAsState(UserResponse.User.defaultUser).value
    val friends = mainViewModel.friendsInfo.observeAsState(emptyList()).value

    mainViewModel.getFriendsInfo()

    Scaffold(bottomBar = { BottomNav(navController = navController) }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item { UserProfileItem(userInfo) }
                items(friends) { friend -> FriendItem(friend) }
            }
        }
    }
}

@Composable
fun UserProfileItem(userInfo: UserResponse.User) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfilePicture(url = "https://avatars.githubusercontent.com/u/127238018?v=4")
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = userInfo.nickname, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = "prime54@naver.com", fontSize = 16.sp)
        }
    }
}

@Composable
fun FriendItem(friend: FriendsResponse.Data) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfilePicture(url = friend.avatar)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = friend.firstName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = friend.email, fontSize = 14.sp)
        }
    }
}

@Composable
fun ProfilePicture(url: String) {
    AsyncImage(
        model =
            ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
        contentDescription = "Profile Picture",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(48.dp),
    )
}
