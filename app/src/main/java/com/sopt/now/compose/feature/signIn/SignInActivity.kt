package com.sopt.now.compose.feature.signIn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class SignInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                SignInScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInPreview() {
    NOWSOPTAndroidTheme {
        SignInScreen()
    }
}