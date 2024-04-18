package com.sopt.now.compose.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val usernameFromSignIn = intent.getStringExtra("username")

        setContent {
            NOWSOPTAndroidTheme {
                MainScreen(usernameFromSignIn ?: "")
            }
        }
    }
}
