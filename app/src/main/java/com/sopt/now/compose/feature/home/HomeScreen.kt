package com.sopt.now.compose.feature.home

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sopt.now.compose.feature.signIn.getUserInfo
import com.sopt.now.compose.model.Friend
import com.sopt.now.compose.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    var userInfo by remember { mutableStateOf<User?>(null) }

    val usernameFromSignIn = navController.previousBackStackEntry?.arguments?.getString("username")

    LaunchedEffect(usernameFromSignIn) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                userInfo = context.getUserInfo()
            } catch (e: Exception) {
                Log.e("MainScreen", "Failed to load user info", e)
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            userInfo?.let {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        model =
                            ImageRequest.Builder(LocalContext.current)
                                .data("https://avatars.githubusercontent.com/u/127238018?v=4")
                                .crossfade(true)
                                .build(),
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(48.dp),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = it.username,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "안녕하세요 저는 감자입니다",
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }
        items(Friend.dummyData) { friend ->
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    model =
                        ImageRequest.Builder(LocalContext.current)
                            .data(friend.profileImageUrl)
                            .crossfade(true)
                            .build(),
                    contentDescription = "Friend Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(48.dp),
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = friend.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = friend.selfDescription,
                        fontSize = 14.sp,
                    )
                    Text(
                        text = friend.profileMusicName,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic,
                    )
                }
            }
        }
    }
}
