package com.sopt.now.compose.feature.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.now.compose.R
import com.sopt.now.compose.feature.signIn.getUserInfo
import com.sopt.now.compose.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainScreen(usernameFromSignIn: String) {
    val context = LocalContext.current
    var userInfo by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(usernameFromSignIn) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                userInfo = context.getUserInfo()
            } catch (e: Exception) {
                Log.e("MainScreen", "Failed to load user info", e)
            }
        }
    }

    userInfo?.let {
        Column(modifier = Modifier.padding(24.dp)) {
            Image(
                painter = painterResource(id = R.drawable.img_main_profile),
                contentDescription = "Profile Picture",
                modifier = Modifier.size(200.dp)
            )
            Text(
                text = stringResource(id = R.string.nickname_label),
                fontSize = 25.sp,
                fontWeight = Bold,
                color = colorResource(id = R.color.purple_500),
                modifier = Modifier.padding(top = 40.dp)
            )
            Text(
                text = it.nickname,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp),
            )
            Text(
                text = stringResource(id = R.string.username_label),
                fontSize = 25.sp,
                fontWeight = Bold,
                color = colorResource(id = R.color.purple_500),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = it.username,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp),
            )
            Text(
                text = stringResource(id = R.string.password_label),
                fontSize = 25.sp,
                fontWeight = Bold,
                color = colorResource(id = R.color.purple_500),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = it.password,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = stringResource(id = R.string.drink_capacity_label),
                fontSize = 25.sp,
                fontWeight = Bold,
                color = colorResource(id = R.color.purple_500),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = it.drinkCapacity.toString(),
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}