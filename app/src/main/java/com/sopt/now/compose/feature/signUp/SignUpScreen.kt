package com.sopt.now.compose.feature.signUp

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.sopt.now.compose.R
import com.sopt.now.compose.data.remote.request.SignUpRequest
import com.sopt.now.compose.feature.common.base.BaseFactory
import com.sopt.now.compose.model.Screen
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

@Composable
fun SignUpScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    val context = LocalContext.current

    val signUpViewModel =
        ViewModelProvider(
            context as ComponentActivity,
            BaseFactory { SignUpViewModel() },
        )[SignUpViewModel::class.java]

    val signUpMessage = signUpViewModel.signUpMessage.observeAsState()

    LaunchedEffect(signUpMessage.value) {
        signUpMessage.value?.let {
            if (it.split("/")[0] == SignUpViewModel.SUCCESS_SIGN_UP) {
                Toast.makeText(
                    context,
                    context.getString(R.string.sign_up_success, it.split("/")[1]),
                    Toast.LENGTH_SHORT,
                ).show()
                navController.navigate(Screen.SignIn.route)
            } else {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = stringResource(id = R.string.sign_up_title))
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
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        OutlinedTextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = { Text(stringResource(id = R.string.nickname_label)) },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text(stringResource(id = R.string.phone_number_label)) },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
        )
        Button(
            onClick = {
                signUpViewModel.performSignUp(
                    SignUpRequest(
                        username,
                        password,
                        nickname,
                        phoneNumber,
                    ),
                )
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
        ) {
            Text(stringResource(id = R.string.sign_up_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpPreview() {
    NOWSOPTAndroidTheme {
        SignUpScreen(navController = NavController(LocalContext.current))
    }
}
