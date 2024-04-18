package com.sopt.now.compose.feature.signIn

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sopt.now.compose.R
import com.sopt.now.compose.feature.main.MainActivity
import com.sopt.now.compose.feature.signUp.SignUpActivity
import com.sopt.now.compose.feature.signUp.dataStore
import com.sopt.now.compose.model.User
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun SignInScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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
                coroutineScope.launch {
                    if (username.isBlank() || password.isBlank()) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.sign_in_blank_error),
                            Toast.LENGTH_SHORT,
                        ).show()
                        return@launch
                    }

                    val userInfo = context.getUserInfo()

                    if (userInfo != null && userInfo.username == username && userInfo.password == password) {
                        Toast.makeText(
                            context,
                            context.getText(R.string.sign_in_success),
                            Toast.LENGTH_SHORT,
                        ).show()
                        val intent =
                            Intent(context, MainActivity::class.java).apply {
                                putExtra("username", username)
                            }
                        context.startActivity(intent)
                        val activity = (context as SignInActivity)
                        activity.finish()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.user_info_not_match),
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
        ) {
            Text("로그인")
        }
        TextButton(
            onClick = {
                val intent = Intent(context, SignUpActivity::class.java)
                context.startActivity(intent)
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

@Preview(showBackground = true)
@Composable
private fun SignInPreview() {
    NOWSOPTAndroidTheme {
        SignInScreen()
    }
}

suspend fun Context.getUserInfo(): User? {
    val usernameKey = stringPreferencesKey("username")
    val passwordKey = stringPreferencesKey("password")
    val nicknameKey = stringPreferencesKey("nickname")
    val drinkCapacityKey = stringPreferencesKey("drinkCapacity")

    val preferences = dataStore.data.first()
    val username = preferences[usernameKey]
    val password = preferences[passwordKey]
    val nickname = preferences[nicknameKey]
    val drinkCapacity = preferences[drinkCapacityKey]?.toFloat()

    return if (username != null && password != null && nickname != null && drinkCapacity != null) {
        User(username, password, nickname, drinkCapacity)
    } else {
        null
    }
}
