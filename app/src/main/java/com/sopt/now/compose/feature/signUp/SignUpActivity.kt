package com.sopt.now.compose.feature.signUp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                SignUpScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpPreview() {
    NOWSOPTAndroidTheme {
        SignUpScreen()
    }
}