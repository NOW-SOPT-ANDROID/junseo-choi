package com.sopt.now.compose.feature.signIn

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.sopt.now.compose.R
import com.sopt.now.compose.data.remote.request.SignInRequest
import com.sopt.now.compose.feature.common.base.BaseFactory
import com.sopt.now.compose.feature.main.MainViewModel
import com.sopt.now.compose.model.Screen
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

@Composable
fun SignInScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    val signInViewModel =
        ViewModelProvider(
            context as ComponentActivity,
            BaseFactory { SignInViewModel() },
        )[SignInViewModel::class.java]

    val mainViewModel =
        ViewModelProvider(
            context,
            BaseFactory { MainViewModel() },
        )[MainViewModel::class.java]

    val signInMessage = signInViewModel.signInMessage.observeAsState()

    signInMessage.value?.let {
        if (it.split("/")[0] == SignInViewModel.SUCCESS_SIGN_IN) {
            Toast.makeText(
                context,
                context.getString(R.string.sign_in_success),
                Toast.LENGTH_SHORT,
            ).show()
            val userId = it.split("/")[1].toInt()
            mainViewModel.getUserInfo(userId)
            navController.navigate(Screen.Home.route)
        } else {
            ShowAnimationMessage(message = it)
        }
    }

    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = stringResource(id = R.string.sign_in_title))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(stringResource(id = R.string.username_label)) },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.password_label)) },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        )
        Button(
            onClick = {
                signInViewModel.performSignIn(SignInRequest(username, password))
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
        ) {
            Text(text = stringResource(id = R.string.sign_in_title))
        }
        TextButton(
            onClick = {
                navController.navigate(Screen.SignUp.route)
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
        ) {
            Text(context.getString(R.string.sign_up_button))
        }
    }
}

@Composable
fun ShowAnimationMessage(message: String) {
    AnimatedVisibility(
        visible = true,
        enter =
            slideInVertically(
                initialOffsetY = { fullHeight -> -fullHeight },
            ),
        exit =
            slideOutVertically(
                targetOffsetY = { fullHeight -> -fullHeight },
            ),
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.secondary,
            shadowElevation = 4.dp,
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInPreview() {
    NOWSOPTAndroidTheme {
        SignInScreen(navController = NavController(LocalContext.current))
    }
}
